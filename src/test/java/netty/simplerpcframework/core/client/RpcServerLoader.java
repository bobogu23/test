package netty.simplerpcframework.core.client;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import netty.simplerpcframework.core.client.channel.RPCMessageSenderHandler;
import org.apache.commons.lang3.StringUtils;

import java.net.InetSocketAddress;
import java.util.Arrays;
import java.util.concurrent.ArrayBlockingQueue;
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

    //    private RPCMessageSenderHandler messageSenderHandler;

    private RPCMessageSenderHandler[] messageSenderHandlers;

    private Lock lock = new ReentrantLock();

    private Condition condition = lock.newCondition();

    //异步初始化与服务端的连接
    public void load(String remoteAddress, int connectionSize) {
        String[] ipAddr = StringUtils.split(remoteAddress, ":");
        if (ipAddr != null && ipAddr.length == 2) {
            String host = ipAddr[0];
            int port = Integer.parseInt(ipAddr[1]);
            InetSocketAddress address = new InetSocketAddress(host, port);

            new RPCMessageSenderInitializeTask(eventLoopGroup, address, this, connectionSize).run();
//            threadPoolExecutor
//                    .submit(new RPCMessageSenderInitializeTask(eventLoopGroup, address, this, connectionSize));
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

    //    public RPCMessageSenderHandler getMessageSenderHandler() {
    //        if (messageSenderHandler != null) {
    //            return messageSenderHandler;
    //        }
    //        lock.lock();
    //        try {
    //            if (messageSenderHandler == null) {
    //                condition.await();
    //            }
    //            return messageSenderHandler;
    //
    //        } catch (InterruptedException e) {
    //            e.printStackTrace();
    //        } finally {
    //            lock.unlock();
    //        }
    //        return messageSenderHandler;
    //    }
    //
    //    public void setMessageSenderHandler(RPCMessageSenderHandler messageSenderHandler) {
    //        lock.lock();
    //        try {
    //            this.messageSenderHandler = messageSenderHandler;
    //            condition.signalAll();
    //
    //        } finally {
    //            lock.unlock();
    //        }
    //    }

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
