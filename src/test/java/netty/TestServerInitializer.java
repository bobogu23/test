package netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * @author:ben.gu
 * @Date:2019/12/16 11:28 PM
 */
public class TestServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast("httpServerCodec",new HttpServerCodec());
        pipeline.addLast("testServerHandler",new TestServerHandler());
    }
}
