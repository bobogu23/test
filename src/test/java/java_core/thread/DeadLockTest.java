package java_core.thread;

/**
 * @author:ben.gu
 * @Date:2020/5/25 2:31 下午
 */
public class DeadLockTest {

    public static void main(String[] args) {

        Object a = new Object();
        Object b = new Object();

        Thread t1 = new Thread(() -> {
            synchronized (a) {
                System.err.println(Thread.currentThread().getName() + ",get monitor a");

                try {
                    Thread.sleep(100L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                synchronized (b) {
                    System.err.println(Thread.currentThread().getName() + ",get monitor b");
                }
            }

        });

        Thread t2 = new Thread(() -> {
            synchronized (b) {
                System.err.println(Thread.currentThread().getName() + ",get monitor b");
                try {
                    Thread.sleep(100L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (a) {
                    System.err.println(Thread.currentThread().getName() + ",get monitor a");
                }
            }

        });
        t1.start();
        t2.start();

    }
}
