package java_core.nio;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * @author:ben.gu
 * @Date:2019/11/25 10:54 PM
 */
public class SelectorTest {

    @Test
    public void testSocketClient() throws IOException {
        SocketChannel channel = SocketChannel.open();
        channel.connect(new InetSocketAddress("localhost", 8080));

        ByteBuffer writeBuffer = ByteBuffer.allocate(1024);
        ByteBuffer readBuffer = ByteBuffer.allocate(1024);

        writeBuffer.put("hello client".getBytes());
        writeBuffer.flip();
        while (true) {
            writeBuffer.rewind();
            channel.write(writeBuffer);

            readBuffer.clear();
            channel.read(readBuffer);
            break;
        }

    }

    @Test
    public void testSocketServer() throws IOException {
        ServerSocketChannel channel = ServerSocketChannel.open();
        channel.socket().bind(new InetSocketAddress("localhost", 8080));
        channel.configureBlocking(false);

        Selector selector = Selector.open();
        //对accept connection 感兴趣
        channel.register(selector, SelectionKey.OP_ACCEPT);

        ByteBuffer writeBuffer = ByteBuffer.allocate(1024);
        ByteBuffer readBuffer = ByteBuffer.allocate(1);

        writeBuffer.put("hello clientServer".getBytes());
        writeBuffer.flip();
        while (true) {

            //准备就绪的channel个数
            int readyNum = selector.select();


            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
//                key.channel();
//                key.selector();
                //附加对象到selectionkey 上，方便识别特定channel
                key.attach(new Object());



                if (key.isAcceptable()) {
                    SocketChannel socketChannel = channel.accept();
                    socketChannel.configureBlocking(false);
                    socketChannel.register(selector, SelectionKey.OP_READ);
                }else if(key.isReadable()){
                    SocketChannel channel1 = (SocketChannel) key.channel();
                    readBuffer.clear();
                    channel1.read(readBuffer);
                    readBuffer.flip();
                    System.err.println("accept:"+new String(readBuffer.array()));

                }else if(key.isWritable()){
                    writeBuffer.rewind();
                    SocketChannel socketChannel = (SocketChannel) key.channel();
                    socketChannel.write(writeBuffer);
                    key.interestOps(SelectionKey.OP_READ);

                }
                iterator.remove();

            }

        }

    }

}
