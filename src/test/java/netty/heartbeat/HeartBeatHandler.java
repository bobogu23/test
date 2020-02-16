package netty.heartbeat;

import java.net.SocketAddress;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

/**
 * @author:ben.gu
 * @Date:2020/2/16 10:36 AM
 */
public class HeartBeatHandler extends ChannelDuplexHandler {
    private byte[] heartBeatData = new byte[] { (byte) 0xc2, (byte) 0x01 };

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.err.println("receive message:" + msg.toString());
        super.channelRead(ctx, msg);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent idleStateEvent = (IdleStateEvent) evt;

            if (idleStateEvent.state() == IdleState.READER_IDLE) {
                SocketAddress socketAddress = ctx.channel().remoteAddress();
                System.err.println("no input data,close channel! remote address:" + socketAddress.toString());

                ctx.close();
            } else if (idleStateEvent.state() == IdleState.WRITER_IDLE) {
                System.err.println("no write,send ping");
                //                ByteBuf buf = ctx.alloc().buffer(6).writeInt(2).writeBytes(heartBeatData);
                ChannelFuture channelFuture = ctx.writeAndFlush("ping");
                channelFuture.addListener(new GenericFutureListener<Future<? super Void>>() {
                    @Override
                    public void operationComplete(Future<? super Void> future) throws Exception {
                        boolean success = future.isSuccess();
                        if (!success) {
                            System.err.println("no write,send ping fail");
                            ctx.close();
                        }
                    }
                });
            }

        }
    }
}
