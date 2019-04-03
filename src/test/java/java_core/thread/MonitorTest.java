package java_core.thread;

import org.junit.Test;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author:ben.gu
 * @Date:2019/3/29 11:18 PM
 */
public class MonitorTest {
    private Lock lock = new ReentrantLock();

    private Condition notFull = lock.newCondition();

    private Condition notEmpty = lock.newCondition();

    private int num = 0;

    public void consume() {
        try {
            lock.lock();
            while (num <= 0) {
                try {
                    notEmpty.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.err.println("consume,num:"+num);
            num--;
            System.err.println("after consume,num:"+num);

            notFull.signal();
        } finally {
            lock.unlock();
        }
    }

    public void provide() {
        try {
            lock.lock();
            while (num > 10) {
                try {
                    notFull.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.err.println("provide,num:"+num);
            num++;
            System.err.println("after provide,num:"+num);

            notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }

    @Test
    public void test() throws InterruptedException {
        MonitorTest monitorTest = new MonitorTest();

        int i = 0;
        while (i<100){
            new Thread(monitorTest::consume).start();
            new Thread(monitorTest::provide).start();
            i++;
        }
        Thread.sleep(10000);

    }

}
