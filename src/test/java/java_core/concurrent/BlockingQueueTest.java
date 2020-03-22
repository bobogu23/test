package java_core.concurrent;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author:ben.gu
 * @Date:2020/3/17 7:43 AM
 */
public class BlockingQueueTest {

    public static void main(String args[]) throws InterruptedException {

        BlockingQueue queue = new ArrayBlockingQueue(10);

        long start  = System.currentTimeMillis();
        Object poll = queue.poll(0, TimeUnit.SECONDS);

        System.err.println("end cost:"+(System.currentTimeMillis()-start));
        System.err.println("poll:"+poll);

    }
}
