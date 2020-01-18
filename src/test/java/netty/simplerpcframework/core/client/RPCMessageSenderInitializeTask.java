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

/**
 * @author:ben.gu
 * @Date:2020/1/6 4:36 PM
 */
public class RPCMessageSenderInitializeTask implements Runnable {
    private Logger logger = LoggerFactory.getLogger(getClass());

    private static final String COLON = ":";

    private final int connectionSize;

    private RpcServerLoader rpcServerLoader;

    private ServiceDiscovery serviceDiscovery;

    //建立多个channel,提高并发量

    public RPCMessageSenderInitializeTask(RpcServerLoader rpcServerLoader, int connectionSize,
            ServiceDiscovery serviceDiscovery) {
        this.rpcServerLoader = rpcServerLoader;
        this.connectionSize = connectionSize;
        this.serviceDiscovery = serviceDiscovery;
    }

    @Override
    public void run() {
    }


}
