package netty.simplerpcframework.core.client.channel;

import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import netty.simplerpcframework.core.client.RPCMessageCallBack;
import netty.simplerpcframework.model.RPCRequest;
import netty.simplerpcframework.model.RPCResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 接收服务端消息,发送客户端消息
 * @author:ben.gu
 * @Date:2020/1/3 5:18 PM
 */
public class RPCMessageSenderHandler extends ChannelInboundHandlerAdapter {
    private Logger logger = LoggerFactory.getLogger(getClass());
    private ConcurrentHashMap<String, RPCMessageCallBack> callBackMap = new ConcurrentHashMap<>();

    private Channel channel;

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        super.channelRegistered(ctx);
        channel = ctx.channel();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //读取响应数据
        RPCResponse response = (RPCResponse) msg;
        String messageId = response.getMessageId();
        if (StringUtils.isNotBlank(messageId)) {
            RPCMessageCallBack callBack = callBackMap.get(messageId);
            if (callBack != null) {
                //触发回调
                callBack.done(response);
                callBackMap.remove(messageId);
            }else {
                logger.error("messageId:{},callback is null",messageId);
            }
        }

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

    //发送消息,返回future回调
    public RPCMessageCallBack sendRequest(RPCRequest request) {
        RPCMessageCallBack callBack = new RPCMessageCallBack(request);
        callBackMap.put(request.getMessageId(), callBack);
        channel.writeAndFlush(request);
        return callBack;
    }

    public Channel getChannel() {
        return channel;
    }

    public void close() {
        channel.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
    }

}
