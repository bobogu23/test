package netty.simplechatroom;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author:ben.gu
 * @Date:2019/12/17 9:53 PM
 */
public class ChatServer {

    public static void main(String args[]) throws InterruptedException {
        EventLoopGroup bossGroup =new NioEventLoopGroup();
        EventLoopGroup workerGroup =new NioEventLoopGroup();

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup,workerGroup).channel(NioServerSocketChannel.class)
                    .childHandler(new ChatServerInitializer());

            ChannelFuture sync = serverBootstrap.bind(9000).sync();
            sync.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
