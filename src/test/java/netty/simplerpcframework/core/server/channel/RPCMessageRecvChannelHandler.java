package netty.simplerpcframework.core.server.channel;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import netty.simplerpcframework.core.server.RPCMessageReceiverExecutor;
import netty.simplerpcframework.core.server.RPCMessageRecvTask;
import netty.simplerpcframework.model.RPCRequest;
import netty.simplerpcframework.model.RPCResponse;

import java.util.Map;
import java.util.concurrent.atomic.LongAdder;

/**
 * @author:ben.gu
 * @Date:2020/1/7 2:03 PM
 */
public class RPCMessageRecvChannelHandler extends ChannelInboundHandlerAdapter {

    private Map<String, Object> handlerMap;
    private LongAdder adder = new LongAdder();

    public RPCMessageRecvChannelHandler(Map<String, Object> handlerMap) {
        this.handlerMap = handlerMap;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //将请求放到线程池中处理
        RPCRequest request = (RPCRequest) msg;
        RPCResponse response = new RPCResponse();
        RPCMessageRecvTask task = new RPCMessageRecvTask(request,response,handlerMap,ctx,adder);
        RPCMessageReceiverExecutor.submit(task);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
