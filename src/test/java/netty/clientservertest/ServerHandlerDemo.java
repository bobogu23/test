package netty.clientservertest;

import java.util.UUID;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author:ben.gu
 * @Date:2019/12/17 9:57 PM
 */
public class ServerHandlerDemo extends SimpleChannelInboundHandler<String> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {

        System.err.println("client address:" + ctx.channel().remoteAddress().toString() + " , " + msg);

        ctx.writeAndFlush("server msg: " + UUID.randomUUID().toString());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        ctx.close();
    }
}
