package netty.simplerpcframework.core.server;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import io.netty.util.concurrent.DefaultThreadFactory;
import netty.simplerpcframework.core.server.thread.AbortPolicyWithReport;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import netty.simplerpcframework.core.server.channel.RPCMessageRecvChannelInitializer;
import netty.simplerpcframework.model.RPCServiceMap;

/**
 * RPC 请求处理器
 * 接收客户端请求
 * 监听指定端口
 * 初始化rpc 服务service
 * @author:ben.gu
 * @Date:2020/1/7 9:39 AM
 */
public class RPCMessageReceiverExecutor implements ApplicationContextAware, InitializingBean {
    private Logger logger = LoggerFactory.getLogger(getClass());

    private static final String DELIMITER = ":";

    private String address;

    private Map<String, Object> handlerMap = new HashMap<>();


    /**
     *     org.apache.catalina.core.StandardThreadExecutor
     *    业务处理属于IO 密集型.线程池 优先扩容到maxThread ,再offer queue ,queue 满了走拒绝策略
     */

    private static ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1000, 1000, 1800, TimeUnit.SECONDS,
            new ArrayBlockingQueue<Runnable>(1000), new DefaultThreadFactory(RPCMessageReceiverExecutor.class),
            new AbortPolicyWithReport());

    public RPCMessageReceiverExecutor(String address) {
        this.address = address;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        //初始化netty 服务端
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        int parallel = Runtime.getRuntime().availableProcessors() ;
        EventLoopGroup workGroup = new NioEventLoopGroup(parallel);

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workGroup).channel(NioServerSocketChannel.class)
                    .childHandler(new RPCMessageRecvChannelInitializer(handlerMap));

            String[] hostPorts = StringUtils.split(this.address, DELIMITER);
            ChannelFuture future = bootstrap.bind(hostPorts[0], Integer.parseInt(hostPorts[1])).sync();
            logger.info("netty rpc server bind ip:{},port:{}", hostPorts[0], Integer.parseInt(hostPorts[1]));
            future.channel().closeFuture().sync();

            //创建core 线程
            threadPoolExecutor.prestartCoreThread();

        } finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }

    }

    public static void submit(Runnable runnable) {
        threadPoolExecutor.submit(runnable);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        //获取rpc 服务service bean

        RPCServiceMap serviceMap;
        try {
            serviceMap = (RPCServiceMap) applicationContext
                    .getBean(Class.forName("netty.simplerpcframework.model.RPCServiceMap"));
        } catch (ClassNotFoundException e) {
            logger.error("bean name of 'netty.simplerpcframework.model.RPCServiceMap' not found");
            throw new RuntimeException("rpc server handler map not foud ");
        }
        Map<String, Object> serviceNameValue = serviceMap.getServiceNameValue();
        handlerMap.putAll(serviceNameValue);
    }
}
