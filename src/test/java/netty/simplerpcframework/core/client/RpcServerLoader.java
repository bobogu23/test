package netty.simplerpcframework.core.client;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import netty.simplerpcframework.core.client.channel.RPCMessageSenderHandler;
import org.apache.commons.lang3.StringUtils;

import java.net.InetSocketAddress;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author:ben.gu
 * @Date:2020/1/6 2:30 PM
 */
public class RpcServerLoader {

    private static volatile RpcServerLoader loader;

    private static final int parallel = Runtime.getRuntime().availableProcessors() * 2;

    private static ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(parallel, parallel + 10, 10,
            TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(parallel));

    private EventLoopGroup eventLoopGroup = new NioEventLoopGroup(parallel);

    private RPCMessageSenderHandler[] messageSenderHandlers;

    private Lock lock = new ReentrantLock();

    private Condition condition = lock.newCondition();

    private int connectionSize;

    private ConcurrentHashMap<String, List<String>> serviceNameAddressMap = new ConcurrentHashMap<>();

    private ConcurrentHashMap<String, RPCMessageSenderHandler[]> serviceNameHandlerMap = new ConcurrentHashMap<>();

    private ConcurrentHashMap<String, RPCMessageSenderHandler[]> addressHandlerMap = new ConcurrentHashMap<>();

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
    public void load(String remoteAddress, int connectionSize) {
        this.connectionSize = connectionSize;
        String[] ipAddr = StringUtils.split(remoteAddress, ":");
        if (ipAddr != null && ipAddr.length == 2) {
            String host = ipAddr[0];
            int port = Integer.parseInt(ipAddr[1]);
            InetSocketAddress address = new InetSocketAddress(host, port);

            new RPCMessageSenderInitializeTask(eventLoopGroup, address, this, connectionSize).run();

        }
    }



    public void unload() {
        if (messageSenderHandlers != null) {
            Arrays.stream(messageSenderHandlers).forEach(RPCMessageSenderHandler::close);
        }
        threadPoolExecutor.shutdown();
        eventLoopGroup.shutdownGracefully();
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

    public RPCMessageSenderHandler[] getMessageSenderHandlers() {
        if (messageSenderHandlers == null) {
            lock.lock();
            try {
                if (messageSenderHandlers == null) {
                    condition.await();
                }
                return messageSenderHandlers;
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
        return messageSenderHandlers;
    }

    public void setMessageSenderHandler(RPCMessageSenderHandler[] messageSenderHandler) {
        lock.lock();
        try {
            messageSenderHandlers = messageSenderHandler;
            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }
}
