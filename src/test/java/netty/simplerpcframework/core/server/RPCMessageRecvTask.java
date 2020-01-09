package netty.simplerpcframework.core.server;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import netty.simplerpcframework.model.RPCRequest;
import netty.simplerpcframework.model.RPCResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.atomic.LongAdder;

/**
 * @author:ben.gu
 * @Date:2020/1/7 7:22 PM
 */
public class RPCMessageRecvTask implements Runnable {
    private Logger logger = LoggerFactory.getLogger(getClass());

    private RPCRequest request;

    private RPCResponse response;

    private Map<String, Object> handlerMap;

    private ChannelHandlerContext ctx;

    private LongAdder adder;

    public RPCMessageRecvTask(RPCRequest request, RPCResponse response, Map<String, Object> handlerMap,
            ChannelHandlerContext ctx, LongAdder adder) {
        this.request = request;
        this.response = response;
        this.handlerMap = handlerMap;
        this.ctx = ctx;
        this.adder = adder;
    }

    @Override
    public void run() {
        long start = System.currentTimeMillis();
        response.setMessageId(request.getMessageId());
        String className = request.getClassName();

        Object service = handlerMap.get(className);
        if (service == null) {
            logger.error("not such bean,name :{}", className);
        }

        Object[] parameterValues = request.getParameterValues();

        String methodName = request.getMethodName();

        Object result = null;
        try {
            Method method = service.getClass().getMethod(methodName, request.getParameterTypes());
            result = method.invoke(service, parameterValues);
        } catch (NoSuchMethodException e) {
            logger.error("bean：{},not such method:{}", className, methodName);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        response.setResult(result);

        ctx.writeAndFlush(response).addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                logger.info("rpc server send response,time cost:{},message id {}",
                        (System.currentTimeMillis() - start), request.getMessageId());
                adder.increment();
                logger.info("count:{}",adder.longValue());
            }
        });
    }
}
