package netty.clientservertest;

import java.util.UUID;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author:ben.gu
 * @Date:2019/12/17 9:57 PM
 */
public class ServerHandlerDemo2 extends SimpleChannelInboundHandler<String> {

    @Override public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.err.println("ServerHandlerDemo2 channel active...");
    }

    @Override public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        System.err.println("ServerHandlerDemo2 channel channelRegistered...");

    }

    @Override public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        System.err.println("ServerHandlerDemo2 handler  Added...");

    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {

        System.err.println("ServerHandlerDemo2 channel read...");


        System.err.println("ServerHandlerDemo2 client address:" + ctx.channel().remoteAddress().toString() + " , " + msg);
        System.err.println("");
        System.err.println("");
//
//        ctx.writeAndFlush("server msg: " + UUID.randomUUID().toString());
        ctx.writeAndFlush("ServerHandlerDemo2 server msg: " + UUID.randomUUID().toString());

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        ctx.close();
    }
}
