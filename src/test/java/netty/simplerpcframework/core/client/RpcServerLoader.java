package netty.simplerpcframework.core.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import netty.simplerpcframework.core.client.channel.RPCMessageSendChannelInitializer;
import netty.simplerpcframework.core.client.channel.RPCMessageSenderHandler;
import netty.simplerpcframework.core.registry.zk.ServiceDiscovery;
import netty.simplerpcframework.core.registry.zk.ServiceNodeCallBack;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author:ben.gu
 * @Date:2020/1/6 2:30 PM
 */
public class RpcServerLoader {
    private Logger logger = LoggerFactory.getLogger(getClass());

    private static final String COLON = ":";

    private static volatile RpcServerLoader loader;

    private static final int parallel = Runtime.getRuntime().availableProcessors() * 2;

    private RPCMessageSenderHandler[] messageSenderHandlers;

    private Lock lock = new ReentrantLock();

    private Condition condition = lock.newCondition();

    private int connectionSize;

    private EventLoopGroup eventLoopGroup;

    private Map<String, RPCMessageSenderHandler[]> serviceNameHandlerMap = new HashMap<>();

    private Map<String, RPCMessageSenderHandler[]> addressHandlerMap = new HashMap<>();

    //异步初始化与服务端的连接
    /**
     * 根据zk 节点信息,建立channel 连接
     * 保存 serviceName 与 RPCMessageSenderHandler 的映射关系
     * 保存 地址与RPCMessageSenderHandler 的映射关系
     *
     * zk 节点信息发生变化
     * 1.节点增加,serviceName 与 RPCMessageSenderHandler 映射关系中加入新的RPCMessageSenderHandler
     * 2.节点删除,serviceName 与 RPCMessageSenderHandler 映射关系中与节点地址相关的handler 关闭，移除
     *
     */
    public void load(String zkAddress, int connectionSize) {
        this.connectionSize = connectionSize;
        this.eventLoopGroup = new NioEventLoopGroup(parallel);
        ServiceNodeCallBack callBack = new ServiceNodeCallBackImpl(this);
        ServiceDiscovery serviceDiscovery = ServiceDiscovery.getInstance(zkAddress, callBack);
        buildMessageHandler(serviceDiscovery.getNodeChildMap());
    }

    public void buildMessageHandler(Map<String, List<String>> nodeChildMap) {
        if (nodeChildMap != null && nodeChildMap.size() > 0) {

            //不存在的地址,新建channel
            nodeChildMap.entrySet().removeIf(e -> this.addressHandlerMap.containsKey(e.getKey()));

            //address <-> channel handler
            Map<String, RPCMessageSenderHandler[]> addressHandlerMap = new HashMap<>(this.addressHandlerMap);

            //serviceName <-> channel handler
            Map<String, RPCMessageSenderHandler[]> serviceNameHandlerMap = new HashMap<>(
                    this.serviceNameHandlerMap);

            Set<String> address = new HashSet<>();

            nodeChildMap.forEach((k, v) -> {
                address.addAll(v);
            });

            CountDownLatch ctd = new CountDownLatch(address.size());

            //等待全部连接建立好
            address.forEach(a -> {
                //建立连接
                String[] split = a.split(COLON);

                InetSocketAddress socketAddress = new InetSocketAddress(split[0], Integer.parseInt(split[1]));

                Bootstrap bootstrap = new Bootstrap();
                bootstrap.group(this.getEventLoopGroup()).channel(NioSocketChannel.class)
                        .option(ChannelOption.SO_KEEPALIVE, true);
                bootstrap.handler(new RPCMessageSendChannelInitializer());
                RPCMessageSenderHandler[] handlers = new RPCMessageSenderHandler[this.connectionSize];
                Lock lock = new ReentrantLock();

                for (int i = 0; i < this.connectionSize; i++) {
                    ChannelFuture channelFuture = bootstrap.connect(socketAddress);

                    channelFuture.addListener(new ChannelFutureListener() {
                        @Override
                        public void operationComplete(ChannelFuture future) throws Exception {

                            if (future.isSuccess()) {
                                logger.info("client connect address:{} success", future.channel().remoteAddress());
                                RPCMessageSenderHandler handler = future.channel().pipeline()
                                        .get(RPCMessageSenderHandler.class);
                                for (int i = 0; i < handlers.length; i++) {
                                    lock.lock();
                                    try {
                                        if (handlers[i] == null) {
                                            handlers[i] = handler;
                                            if (i == handlers.length - 1) {
                                                addressHandlerMap.put(a, handlers);

                                            }
                                            break;
                                        }
                                    } finally {
                                        lock.unlock();
                                    }

                                }
                            }
                            ctd.countDown();
                        }
                    });

                }

            });

            try {
                ctd.await();
            } catch (InterruptedException e) {
                logger.error("wait connect channel error:", e);
            }
            //组装serviceName,handler map
            nodeChildMap.forEach((k, v) -> v.forEach(addr -> {
                RPCMessageSenderHandler[] handlers = addressHandlerMap.get(addr);
                RPCMessageSenderHandler[] serviceHandlers = serviceNameHandlerMap.get(k);
                if (serviceHandlers == null) {
                    serviceNameHandlerMap.put(k, handlers);
                } else {
                    serviceNameHandlerMap.put(k, ArrayUtils.addAll(serviceHandlers, handlers));
                }
            }));
            this.setMessageSenderHandler(serviceNameHandlerMap, addressHandlerMap);
        }

    }

    public EventLoopGroup getEventLoopGroup() {
        return eventLoopGroup;
    }

    public int getConnectionSize() {
        return connectionSize;
    }

    public void unload() {
        if (messageSenderHandlers != null) {
            Arrays.stream(messageSenderHandlers).forEach(RPCMessageSenderHandler::close);
        }
    }

    public static RpcServerLoader getInstance() {
        if (loader == null) {
            synchronized (RpcServerLoader.class) {
                if (loader == null) {
                    loader = new RpcServerLoader();
                }
            }
        }
        return loader;
    }

    public RPCMessageSenderHandler[] getMessageSenderHandlers(String serviceName) {
        if (serviceNameHandlerMap.size() == 0) {
            lock.lock();
            try {
                if (serviceNameHandlerMap.size() == 0) {
                    condition.await(3,TimeUnit.SECONDS);
                }
                if(serviceNameHandlerMap.size() == 0){
                    throw new RuntimeException("get message sender handler error,server may be not exist");
                }
                return serviceNameHandlerMap.get(serviceName);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
        return serviceNameHandlerMap.get(serviceName);
    }

    public void setMessageSenderHandler(Map<String, RPCMessageSenderHandler[]> serviceNameHandlerMap,
            Map<String, RPCMessageSenderHandler[]> addressHandlerMap) {
        lock.lock();
        try {
            this.serviceNameHandlerMap.putAll(serviceNameHandlerMap);
            this.addressHandlerMap.putAll(addressHandlerMap);
            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public Map<String, RPCMessageSenderHandler[]> getServiceNameHandlerMap() {
        return serviceNameHandlerMap;
    }

    public void setServiceNameHandlerMap(Map<String, RPCMessageSenderHandler[]> serviceNameHandlerMap) {
        this.serviceNameHandlerMap = serviceNameHandlerMap;
    }

    public Map<String, RPCMessageSenderHandler[]> getAddressHandlerMap() {
        return addressHandlerMap;
    }

    public void setAddressHandlerMap(Map<String, RPCMessageSenderHandler[]> addressHandlerMap) {
        this.addressHandlerMap = addressHandlerMap;
    }
}
