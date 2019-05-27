package java_core.nio;

import org.junit.Test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @author:ben.gu
 * @Date:2019/5/22 9:09 PM
 */
public class SocketChannelTest {

    @Test
    public void testSocketChannel() throws IOException, InterruptedException {
        SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("localhost", 8001));

        int size = 100;

        CyclicBarrier barrier = new CyclicBarrier(size,()->{
            System.err.println("start request...");
        });

        int i = 0;
        while (i < size) {

            new Thread(() -> {
                try {
                    barrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
                ByteBuffer buffer = ByteBuffer.allocate(1024);
                try {
                    buffer.put("hahah".getBytes("utf-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                // 写入数据到网络连接中
                buffer.flip();
                while (buffer.hasRemaining()) {
                    try {
                        socketChannel.write(buffer);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                buffer.clear();
            }).start();
            ++i;
        }

    }

    @Test
    public void testServerSocketChannel() throws IOException, InterruptedException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        serverSocketChannel.socket().bind(new InetSocketAddress(8001));

        while (true) {
            SocketChannel accept = serverSocketChannel.accept();
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            int read = accept.read(buffer);
            buffer.flip();
            if (read != -1) {
                System.err.println("data:" + new String(buffer.array()).trim());
            }

        }

    }

}
