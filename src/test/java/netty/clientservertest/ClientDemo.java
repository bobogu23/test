package netty.clientservertest;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author:ben.gu
 * @Date:2019/12/17 9:50 PM
 */
public class ClientDemo {

    public static void main(String args[]) throws Exception{
        EventLoopGroup workerGroup =new NioEventLoopGroup();

        try {
            Bootstrap serverBootstrap = new Bootstrap();
            serverBootstrap.group(workerGroup).channel(NioSocketChannel.class)
                    .handler(new ClientInitializerDemo());

            ChannelFuture sync = serverBootstrap.connect("localhost",9000).sync();
            sync.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
        }
    }
}
