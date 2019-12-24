package java_core.nio;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;

import org.junit.Test;

/**
 * @author:ben.gu
 * @Date:2019/4/27 10:07 PM
 */
public class ChannelTest {

    @Test
    public void testFileChannel() throws Exception {

        RandomAccessFile raf = new RandomAccessFile("src/LoopTest.java", "rw");
        FileChannel channel = raf.getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        int read = channel.read(byteBuffer);
        while (read != -1) {
            byteBuffer.flip();
            while (byteBuffer.hasRemaining()) {
                System.err.println((char) byteBuffer.get());
            }
            byteBuffer.compact();
            read = channel.read(byteBuffer);
        }
        if (raf != null) {
            raf.close();
        }

    }

    @Test
    public void testMarkRest() throws Exception {

        RandomAccessFile raf = new RandomAccessFile("src/LoopTest.java", "rw");
        FileChannel channel = raf.getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        int read = channel.read(byteBuffer);
        byteBuffer.flip();
        int position = 0;

        while (byteBuffer.hasRemaining()) {

            position = byteBuffer.position();
            System.err.println("position:" + position + ",data:" + (char) byteBuffer.get());

            if (position == 5) {
                byteBuffer.mark();
            }
            if (position == 10) {
                byteBuffer.reset();
            }

        }

        if (raf != null) {
            raf.close();
        }

    }

    private static String content = "aabbccddaaa11111";

    private static String sourceFilePath = "/javanio/file_channel_source.txt";

    private static String targetFilePath = "/javanio/file_channel_target.txt";

    /**
     * 对应底层操作系统 sendfile 方式的零拷贝.具体实现见jvm 源码。
     * 优先使用 transferToDirectly 以sendfile 零拷贝的方式尝试数据拷贝。如果系统内核不支持，执行transferToTrustedChannel，以mmap的零拷贝方式进行内存映射。
     * 如果还不支持, 执行transferToArbitraryChannel ,以传统的IO方式读写，fromchannle->buffer->sourcechannel
     * @throws IOException
     */
    @Test
    public void testTransferTo() throws IOException {

        Path sourcePath = Paths.get(getClass().getResource(sourceFilePath).getPath());

        byte[] bytes = content.getBytes(Charset.forName("utf-8"));

        try (FileChannel fromChannel = FileChannel.open(sourcePath, StandardOpenOption.READ,
                StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING)) {

            fromChannel.write(ByteBuffer.wrap(bytes));

        } catch (IOException e) {
            e.printStackTrace();
        }

        try (FileChannel fromChannel = new RandomAccessFile(getClass().getResource(sourceFilePath).getPath(), "rw")
                .getChannel();
                FileChannel toChannel = new RandomAccessFile(getClass().getResource(targetFilePath).getPath(), "rw")
                        .getChannel()) {
            long position = 0L;
            long offset = fromChannel.size();

            fromChannel.transferTo(position, offset, toChannel);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testTransferFrom() throws IOException {

        Path sourcePath = Paths.get(getClass().getResource(sourceFilePath).getPath());

        byte[] bytes = content.getBytes(Charset.forName("utf-8"));

        try (FileChannel fromChannel = FileChannel.open(sourcePath, StandardOpenOption.READ,
                StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING)) {

            fromChannel.write(ByteBuffer.wrap(bytes));

        } catch (IOException e) {
            e.printStackTrace();
        }

        try (FileChannel fromChannel = new RandomAccessFile(getClass().getResource(sourceFilePath).getPath(), "rw")
                .getChannel();
                FileChannel toChannel = new RandomAccessFile(getClass().getResource(targetFilePath).getPath(), "rw")
                        .getChannel()) {
            long position = 0L;
            long offset = fromChannel.size();

            toChannel.transferFrom(fromChannel, position, offset);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * filechannel 继承了scattering gathering channel ，可以实现对多个buffer进行读写
     */
    @Test
    public void testScatteringGathering() throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open().bind(new InetSocketAddress(9000));
        SocketChannel channel = serverSocketChannel.accept();

        int length = 7;
        ByteBuffer[] buffers = new ByteBuffer[3];
        buffers[0] = ByteBuffer.allocate(1);
        buffers[1] = ByteBuffer.allocate(2);
        buffers[2] = ByteBuffer.allocate(4);
        long num = 0;
        while (true) {

            long read = channel.read(buffers);
            num = read + num;
            Arrays.asList(buffers).forEach(b -> {
                try {
                    System.err.println(new String(b.array(), 0, b.position(), "utf-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            });

            if (num >= length) {
                Arrays.asList(buffers).forEach(b -> {
                    b.flip();
                });

                long writeNum = 0;
                while (writeNum < length) {
                    long write = channel.write(buffers);
                    writeNum += write;
                }
                break;

            }

            //            Arrays.asList(buffers).forEach(b -> {
            //                b.clear();
            //            });
        }

    }
}
