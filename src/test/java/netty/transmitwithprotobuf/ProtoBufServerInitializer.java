//package netty.transmitwithprotobuf;
//
//import io.netty.channel.ChannelInitializer;
//import io.netty.channel.ChannelPipeline;
//import io.netty.channel.socket.SocketChannel;
//import io.netty.handler.codec.protobuf.ProtobufDecoder;
//import io.netty.handler.codec.protobuf.ProtobufEncoder;
//import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
//import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
//
///**
// * @author:ben.gu
// * @Date:2019/12/19 11:05 PM
// */
//public class ProtoBufServerInitializer extends ChannelInitializer<SocketChannel> {
//    @Override
//    protected void initChannel(SocketChannel ch) throws Exception {
//        ChannelPipeline pipeline = ch.pipeline();
//        pipeline.addLast(new ProtobufDecoder(Animal.Person.getDefaultInstance()));
//        pipeline.addLast(new ProtobufEncoder());
//        pipeline.addLast(new ProtobufVarint32FrameDecoder());
//        pipeline.addLast(new ProtobufVarint32LengthFieldPrepender());
//        pipeline.addLast(new ProtoBufServerHandler());
//    }
//}
