package redis.redisson;

import org.redisson.Redisson;
import org.redisson.api.RCountDownLatch;
import org.redisson.api.RLock;
import org.redisson.api.RSemaphore;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import java.util.concurrent.TimeUnit;

/**
 * @author:ben.gu
 * @Date:2021/9/21 10:10 上午
 */
public class RedissonTest {

    public static void main(String[] args) throws InterruptedException {
//        192.168.60.48
        Config config = new Config();
        config.useClusterServers().addNodeAddress("redis://192.168.60.48:7001").
                addNodeAddress("redis://192.168.60.48:7003").
                addNodeAddress("redis://192.168.60.48:7002").setPassword("xmly123456")
        ;
        RedissonClient redissonClient = Redisson.create(config);
        RLock lock = redissonClient.getFairLock("testLock11111");
        lock.tryLock(20, 10,TimeUnit.SECONDS);
        System.err.println("get lock");
        lock.unlock();
        System.err.println("release lock");
        System.err.println("redisson client is shutdown:"+redissonClient.isShutdown());
        redissonClient.shutdown();

        RSemaphore semaphore = redissonClient.getSemaphore("semaphoreTest");

        RCountDownLatch countDownLatch = redissonClient.getCountDownLatch("countDownLatchTest");

    }
}
