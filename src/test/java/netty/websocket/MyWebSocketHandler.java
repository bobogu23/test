package netty.websocket;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.time.LocalTime;

/**
 * @author:ben.gu
 * @Date:2019/12/18 9:51 PM
 */
public class MyWebSocketHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        System.err.println("receive msg:"+msg.text());
        ctx.channel().writeAndFlush(new TextWebSocketFrame("服务端时间:"+ LocalTime.now()));
    }
}
