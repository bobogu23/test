package netty.clientservertest;

import java.util.UUID;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author:ben.gu
 * @Date:2019/12/17 9:57 PM
 */
public class ServerHandlerDemo extends SimpleChannelInboundHandler<String> {

    @Override public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.err.println("channel active...");
    }

    @Override public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        System.err.println("channel channelRegistered...");

    }

    @Override public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        System.err.println("handler  Added...");

    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {

        System.err.println("channel read...");


        System.err.println("client address:" + ctx.channel().remoteAddress().toString() + " , " + msg);
        System.err.println("");
        System.err.println("");

        //"server msg: " + UUID.randomUUID().toString()
//        ctx.writeAndFlush("server msg: " + UUID.randomUUID().toString());
//        ctx.writeAndFlush()
        ctx.fireChannelRead(msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        ctx.close();
    }
}
