package java_core.nio;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import org.junit.Test;

/**
 * @author:ben.gu
 * @Date:2019/4/27 10:07 PM
 */
public class ChannelTest {

    @Test
    public void testFileChannel() throws Exception {

        RandomAccessFile raf =new RandomAccessFile("src/LoopTest.java","rw");
        FileChannel channel = raf.getChannel();
        ByteBuffer byteBuffer =ByteBuffer.allocate(1024);
        int read = channel.read(byteBuffer);
        while (read != -1){
            byteBuffer.flip();
            while (byteBuffer.hasRemaining()){
                System.err.println((char)byteBuffer.get());
            }
            byteBuffer.compact();
            read = channel.read(byteBuffer);
        }

        if(raf != null){
            raf.close();
        }

    }

}
