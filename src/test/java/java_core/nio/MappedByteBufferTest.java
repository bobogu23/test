package java_core.nio;

import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import org.junit.Assert;
import org.junit.Test;

/**
 * 基于内存映射(mmap)零拷贝方式的实现.
 * mmap linux 系统提供的一种内存映射文件方法。将一个进程的地址空间中的一段虚拟地址映射到磁盘文件地址。
 * 将内核读缓冲区(reader buffer)的地址与用户空间的缓冲区(user buffer)进行映射。
 * 使得内核缓冲区与应用内存共享，省去将数据从内核读缓冲区拷贝到用户缓冲区。
 *
 * MappedByteBuffer 通过 {@link
 * java.nio.channels.FileChannel#map FileChannel.map} 方法创建。具体使用见API。操作小文件不划算，适用于操作大文件.
 * @author:ben.gu
 * @Date:2019/12/23 2:31 PM
 */
public class MappedByteBufferTest {

    static String content = "test111xxxxx";

    static String fileName = "/javanio/mmap_test.txt";

    @Test
    public void writeToFile() throws IOException {
        Path path = Paths.get(getClass().getResource(fileName).getPath());
        byte[] bytes = content.getBytes(Charset.forName("utf-8"));

        FileChannel fileChannel = FileChannel.open(path, StandardOpenOption.READ, StandardOpenOption.WRITE,
                StandardOpenOption.TRUNCATE_EXISTING);
        MappedByteBuffer mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_WRITE, 0, bytes.length);

        if (mappedByteBuffer != null) {
            mappedByteBuffer.put(bytes);
            //强制将缓冲区的内容刷新到存储设备
            mappedByteBuffer.force();
        }
    }

    @Test
    public void readFileByMappedByteBuffer() {
        Path path = Paths.get(getClass().getResource(fileName).getPath());
        int length = content.getBytes(Charset.forName("utf-8")).length;

        try (FileChannel fileChannel = FileChannel.open(path, StandardOpenOption.READ)) {
            MappedByteBuffer map = fileChannel.map(FileChannel.MapMode.READ_ONLY, 0, length);
            if (map != null) {

                byte[] bytes = new byte[length];
                map.get(bytes);

                String content = new String(bytes, StandardCharsets.UTF_8);
                Assert.assertTrue(content.equals(content));
            }

        } catch (IOException e) {

        }

    }

}
