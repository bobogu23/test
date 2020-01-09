package netty.simplerpcframework.core.client;

import java.lang.reflect.Proxy;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author:ben.gu
 * @Date:2020/1/3 8:10 PM
 */
public class RPCServiceProxy {
    private RpcServerLoader loader = RpcServerLoader.getInstance();

    private AtomicInteger index = new AtomicInteger();

    //初始化, 建立与服务端的链接
    public RPCServiceProxy(String remoteAddress) {
        loader.load(remoteAddress, 4);
    }

    public void stop() {
        loader.unload();
    }

    public <T> T getProxy(Class<T> customServiceInterface) {
        int num = index.getAndDecrement();

        return (T) Proxy.newProxyInstance(customServiceInterface.getClassLoader(),
                new Class[] { customServiceInterface }, new RPCMessageSenderProxy(getPositive(num)));
    }

    private int getPositive(int num) {
        return Integer.MAX_VALUE & num;
    }
}
