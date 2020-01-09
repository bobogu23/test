package netty.simplerpcframework.core.client;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import io.netty.channel.Channel;
import netty.simplerpcframework.core.client.channel.RPCMessageSenderHandler;
import netty.simplerpcframework.model.RPCRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 将RPC java对象的方法调用 转换成RPC request 发送出去
 * @author:ben.gu
 * @Date:2020/1/3 4:00 PM
 */
public class RPCMessageSenderProxy implements InvocationHandler {
    private Logger logger = LoggerFactory.getLogger(getClass());

    private  int index ;
    public RPCMessageSenderProxy(int index) {
        this.index = index;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //封装成RPCRequest
        RPCRequest request = new RPCRequest();
        request.setMessageId(UUID.randomUUID().toString());
        request.setClassName(method.getDeclaringClass().getName());
        request.setMethodName(method.getName());
        request.setParameterTypes(method.getParameterTypes());
        request.setParameterValues(args);
        logger.info("request class name :{}", request.getClassName());

        RPCMessageSenderHandler messageSenderHandler = null;

        RPCMessageSenderHandler[] messageSenderHandlers = RpcServerLoader.getInstance().getMessageSenderHandlers();
        int count = index;
        for (int i = count; i < messageSenderHandlers.length + count; i++) {
            int index  = i % messageSenderHandlers.length;
            RPCMessageSenderHandler handler = messageSenderHandlers[index];
            if(handler != null){
                Channel channel = handler.getChannel();
                if (channel.isActive()) {
                    if (channel.isWritable()) {
                        messageSenderHandler = handler;
                        System.err.println("channel index "+index);
                        break;
                    }
                    //写缓冲区满了，选下一个
                }
            }

        }
        if (messageSenderHandler != null) {
            //发送请求
            RPCMessageCallBack rpcMessageCallBack = messageSenderHandler.sendRequest(request);
            return rpcMessageCallBack.get();

        }
        logger.error("not active channel");
        return null;
    }
}
