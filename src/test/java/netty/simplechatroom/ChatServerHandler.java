package netty.simplechatroom;

import java.util.UUID;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * @author:ben.gu
 * @Date:2019/12/17 9:57 PM
 */
public class ChatServerHandler extends SimpleChannelInboundHandler<String> {

    /**
     * 连接的channel 集合
     */
    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        Channel channel = ctx.channel();
        channelGroup.forEach(c->{
            if(c != channel){
                c.writeAndFlush(channel.remoteAddress()+" 发送消息 " +msg+"\n");
            }else {
                c.writeAndFlush(" 自己 " +msg+"\n");
            }
        });

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        ctx.close();
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        System.err.println("client:" + ctx.channel().remoteAddress() + " 已经下线");
        super.channelUnregistered(ctx);
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        System.err.println("client:" + ctx.channel().remoteAddress() + " 已经上线");

        super.channelRegistered(ctx);
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();

        channelGroup.writeAndFlush("服务器:" + channel.remoteAddress() + "加入\r\n");
        channelGroup.add(channel);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channelGroup.writeAndFlush("服务器:" + channel.remoteAddress() + "离开\r\n");
    }
}
