package netty.clientservertest;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author:ben.gu
 * @Date:2019/12/17 10:08 PM
 */
public class ClientHandlerDemo extends SimpleChannelInboundHandler<String> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.err.println("server address:" + ctx.channel().remoteAddress());
        System.err.println("client output:" + msg);
        ctx.writeAndFlush("from client:" + System.currentTimeMillis());
    }

    @Override public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        ctx.writeAndFlush("xixi!!");
    }


}
