package java_core.java8;

import org.junit.Test;

import java.util.stream.LongStream;

/**
 * @author:ben.gu
 * @Date:2020/6/24 12:18 上午
 */
public class StreamTest {

    @Test
    public void testReduce(){
        long result = LongStream.rangeClosed(1, 100).reduce(0, Long::min);
        System.err.println("result:"+result);

    }
}
