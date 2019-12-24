package netty.transmitwithprotobuf;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author:ben.gu
 * @Date:2019/12/17 10:47 PM
 */
public class ProtoBufClientHandler extends SimpleChannelInboundHandler<String> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.err.println("msg:" + msg);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(Animal.Person.newBuilder().setId(1).setName("aa").build());
    }
}
