package java_core.thread;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author:ben.gu
 * @Date:2019/5/11 9:00 PM
 */
public class ScheduledThreadPoolTest {

    public static void main(String args[]) {

        ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new
                ScheduledThreadPoolExecutor(1);

        scheduledThreadPoolExecutor.scheduleAtFixedRate(()->{
            System.err.println("hello");
        },1,2, TimeUnit.SECONDS);

    }
}
