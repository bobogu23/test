package netty.simplerpcframework.core.client;

import java.net.InetSocketAddress;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import io.netty.channel.nio.NioEventLoopGroup;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import netty.simplerpcframework.core.client.channel.RPCMessageSendChannelInitializer;
import netty.simplerpcframework.core.client.channel.RPCMessageSenderHandler;
import netty.simplerpcframework.core.registry.zk.ServiceDiscovery;
import netty.simplerpcframework.core.registry.zk.ServiceNodeCallBack;

/**
 * @author:ben.gu
 * @Date:2020/1/6 4:36 PM
 */
public class RPCMessageSenderInitializeTask implements Runnable {
    private Logger logger = LoggerFactory.getLogger(getClass());

    private final int connectionSize;

    private EventLoopGroup eventLoopGroup;

    private RpcServerLoader rpcServerLoader;

    private int parallel;

    private ServiceNodeCallBack serviceNodeCallBack;

    private String zkAddress;

    private ServiceDiscovery serviceDiscovery;

    private static final String COMMA = ":";

    //建立多个channel,提高并发量

    //    public RPCMessageSenderInitializeTask(EventLoopGroup eventLoopGroup, InetSocketAddress serverAddress,
    //            RpcServerLoader rpcServerLoader, int connectionSize) {
    //        this.eventLoopGroup = eventLoopGroup;
    //        this.serverAddress = serverAddress;
    //        this.rpcServerLoader = rpcServerLoader;
    //        this.connectionSize = connectionSize;
    //    }

    public RPCMessageSenderInitializeTask(int parallel, RpcServerLoader rpcServerLoader, int connectionSize,
            ServiceNodeCallBack serviceNodeCallBack, String zkAddress) {
        this.parallel = parallel;
        this.rpcServerLoader = rpcServerLoader;
        this.connectionSize = connectionSize;
        this.serviceNodeCallBack = serviceNodeCallBack;
        this.zkAddress = zkAddress;
        this.serviceDiscovery = ServiceDiscovery.getInstance(zkAddress, serviceNodeCallBack);
        this.eventLoopGroup = new NioEventLoopGroup(parallel);
    }

    @Override
    public void run() {
        //serviceName <-> address List
        Map<String, List<String>> nodeChildMap = serviceDiscovery.getNodeChildMap();
        //address <-> channel handler
        Map<String, RPCMessageSenderHandler[]> addressHandlerMap = new HashMap<>();


        //serviceName <-> channel handler
        Map<String, RPCMessageSenderHandler[]> serviceNameHandlerMap = new HashMap<>();

        if (nodeChildMap != null && nodeChildMap.size() > 0) {
            Set<String> address = new HashSet<>();

            nodeChildMap.forEach((k, v) -> {
                address.addAll(v);
            });

            CountDownLatch ctd = new CountDownLatch(address.size());

            //等待全部连接建立好
            address.forEach(a -> {
                //建立连接
                String[] split = a.split(COMMA);

                InetSocketAddress socketAddress = new InetSocketAddress(split[0], Integer.parseInt(split[1]));

                Bootstrap bootstrap = new Bootstrap();
                bootstrap.group(this.eventLoopGroup).channel(NioSocketChannel.class)
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
            nodeChildMap.forEach((k,v)-> v.forEach(addr->{
                RPCMessageSenderHandler[] handlers = addressHandlerMap.get(addr);
                RPCMessageSenderHandler[] serviceHandlers = serviceNameHandlerMap.get(k);
                if(serviceHandlers == null){
                    serviceNameHandlerMap.put(k,serviceHandlers);
                }else {
                    serviceNameHandlerMap.put(k, ArrayUtils.addAll(serviceHandlers,handlers));
                }
            }));
            rpcServerLoader.setMessageSenderHandler(serviceNameHandlerMap,addressHandlerMap);
        }

    }
}
