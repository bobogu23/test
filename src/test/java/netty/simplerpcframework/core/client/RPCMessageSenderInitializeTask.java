package netty.simplerpcframework.core.client;

import java.net.InetSocketAddress;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import netty.simplerpcframework.core.client.channel.RPCMessageSendChannelInitializer;
import netty.simplerpcframework.core.client.channel.RPCMessageSenderHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author:ben.gu
 * @Date:2020/1/6 4:36 PM
 */
public class RPCMessageSenderInitializeTask implements Runnable {
    private final int connectionSize;

    private Logger logger = LoggerFactory.getLogger(getClass());

    private EventLoopGroup eventLoopGroup;

    private InetSocketAddress serverAddress;

    private RpcServerLoader rpcServerLoader;

    //建立多个channel,提高并发量

    public RPCMessageSenderInitializeTask(EventLoopGroup eventLoopGroup, InetSocketAddress serverAddress,
            RpcServerLoader rpcServerLoader, int connectionSize) {
        this.eventLoopGroup = eventLoopGroup;
        this.serverAddress = serverAddress;
        this.rpcServerLoader = rpcServerLoader;
        this.connectionSize = connectionSize;
    }

    @Override
    public void run() {

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(eventLoopGroup).channel(NioSocketChannel.class).option(ChannelOption.SO_KEEPALIVE, true);
        bootstrap.handler(new RPCMessageSendChannelInitializer());
        RPCMessageSenderHandler[] handlers = new RPCMessageSenderHandler[connectionSize];
        Lock lock = new ReentrantLock();

        for (int i = 0; i < connectionSize; i++) {
            ChannelFuture channelFuture = bootstrap.connect(serverAddress);

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
                                        rpcServerLoader.setMessageSenderHandler(handlers);
                                    }
                                    break;
                                }
                            } finally {
                                lock.unlock();
                            }

                        }
                    }
                }
            });

        }

    }
}
