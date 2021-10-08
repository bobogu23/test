//package netty.transmitwithprotobuf;
//
//import java.io.IOException;
//
//import io.netty.bootstrap.Bootstrap;
//import io.netty.channel.Channel;
//import io.netty.channel.EventLoopGroup;
//import io.netty.channel.nio.NioEventLoopGroup;
//import io.netty.channel.socket.nio.NioSocketChannel;
//
///**
// * @author:ben.gu
// * @Date:2019/12/17 10:45 PM
// */
//public class ProtocBufClient {
//
//    public static void main(String args[]) throws InterruptedException, IOException {
//        EventLoopGroup workerGroup =new NioEventLoopGroup();
//
//        try {
//            Bootstrap serverBootstrap = new Bootstrap();
//            serverBootstrap.group(workerGroup).channel(NioSocketChannel.class)
//                    .handler(new ProtoBufClientInitializer());
//
//            Channel channel = serverBootstrap.connect("localhost", 9000).channel();
//
//            channel.closeFuture().sync();
//
//        } finally {
//            workerGroup.shutdownGracefully();
//        }
//    }
//}
