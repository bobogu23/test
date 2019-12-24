package netty.simplechatroom;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author:ben.gu
 * @Date:2019/12/17 10:45 PM
 */
public class ChatClient {

    public static void main(String args[]) throws InterruptedException, IOException {
        EventLoopGroup workerGroup =new NioEventLoopGroup();

        try {
            Bootstrap serverBootstrap = new Bootstrap();
            serverBootstrap.group(workerGroup).channel(NioSocketChannel.class)
                    .handler(new ChatClientInitializer());

            Channel channel = serverBootstrap.connect("localhost", 9000).channel();

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            for (;;){
                channel.writeAndFlush(reader.readLine()+"\r\n");
            }

        } finally {
            workerGroup.shutdownGracefully();
        }
    }
}
