package java_core.thread;

import java.util.concurrent.*;

/**
 * @author:ben.gu
 * @Date:2020/4/23 3:27 下午
 */
public class DaemonThreadPoolTest {

    public static void main(String[] args) throws InterruptedException {

        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, Integer.MAX_VALUE, 0, TimeUnit.NANOSECONDS,
                new SynchronousQueue<Runnable>(), new ThreadFactory() {
            @Override public Thread newThread(Runnable r) {
                Thread thread = new Thread(r, "com.ximalaya.nacos.naming.client.listener");
                thread.setDaemon(true);

                return thread;
            }
        });


        threadPoolExecutor.execute(()->{
            System.err.println("hello111!!!!");
        });
        threadPoolExecutor.execute(()->{
            System.err.println("hello2222!!!!");
        });
        Thread.sleep(1000);
        int poolSize = threadPoolExecutor.getPoolSize();
        System.err.println("poolSize:"+poolSize);

        Thread.sleep(100000);

    }
}
