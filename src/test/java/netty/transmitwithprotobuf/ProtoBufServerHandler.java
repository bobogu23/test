package netty.transmitwithprotobuf;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author:ben.gu
 * @Date:2019/12/19 11:11 PM
 */
public class ProtoBufServerHandler extends SimpleChannelInboundHandler<Animal.Person> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Animal.Person msg) throws Exception {

        System.err.println("msg:"+msg.toString());
    }
}
