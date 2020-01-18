package netty.simplerpcframework.core.client;

import java.lang.reflect.Proxy;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author:ben.gu
 * @Date:2020/1/3 8:10 PM
 */
public class RPCServiceProxy {
    private RpcServerLoader loader = RpcServerLoader.getInstance();

    //每个service 代理对应一个计数器,轮询使用handler
    private ConcurrentHashMap<Class, AtomicInteger> serviceIndexMap = new ConcurrentHashMap<>();

    //初始化, 建立与服务端的链接
    public RPCServiceProxy(String zkAddress) {
        loader.load(zkAddress, 4);
    }

    public void stop() {
        loader.unload();
    }

    public <T> T getProxy(Class<T> customServiceInterface) {
        AtomicInteger atomicInteger = serviceIndexMap.putIfAbsent(customServiceInterface, new AtomicInteger());

        int num = atomicInteger != null ? atomicInteger.incrementAndGet()
                : serviceIndexMap.get(customServiceInterface).incrementAndGet();

        return (T) Proxy.newProxyInstance(customServiceInterface.getClassLoader(),
                new Class[] { customServiceInterface }, new RPCMessageSenderProxy(getPositive(num)));
    }

    private int getPositive(int num) {
        return Integer.MAX_VALUE & num;
    }
}
