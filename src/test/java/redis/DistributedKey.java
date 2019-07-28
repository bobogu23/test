package redis;

import com.google.common.collect.Lists;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import redis.clients.jedis.Jedis;

import java.util.Collections;
import java.util.UUID;

/**
 * @author:ben.gu
 * @Date:2019/4/28 11:34 AM
 */
public class  DistributedKey {
    private Jedis jedis;

    private static final String SUCCESS = "OK";

    private static final String SET_IF_NOT_EXIT = "NX";

    private static final String SET_WITH_EXPIRE_TIME = "PX";

    private static final Long RELEASE_SUCCESS = 1L;

    public DistributedKey() {
        jedis = new Jedis("192.168.1.174", 6382);
        jedis.auth("jredis123456");
        jedis.select(0);
    }

    public Jedis getJedis() {
        return jedis;
    }

    @Test
    public void testSet() {

        String r = new DistributedKey().getJedis().set("a", "hello");
        System.err.println("r :" + r);
    }

    @Test
    public void testLock() {
        String key = "DistributedKey-1";
        String requestId = UUID.randomUUID().toString();
        boolean lock = new DistributedKey().lock(key, requestId, 10000);
        System.err.println("requestId:" + requestId);
        Assert.assertTrue("requestId :" + requestId + " lock failed", lock);

        requestId = UUID.randomUUID().toString();
        lock = new DistributedKey().lock(key, requestId, 10000);
        System.err.println("requestId:" + requestId);
        Assert.assertTrue("requestId :" + requestId + " lock failed", lock);
    }

    @Test
    public void testLockAfterExpire() {
        String key = "DistributedKey-1";
        String requestId = UUID.randomUUID().toString();
        boolean lock = new DistributedKey().lock(key, requestId, 10000);
        System.err.println("requestId:" + requestId);
        Assert.assertTrue("requestId :" + requestId + " lock failed", lock);
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        requestId = UUID.randomUUID().toString();
        lock = new DistributedKey().lock(key, requestId, 10000);
        System.err.println("requestId:" + requestId);
        Assert.assertTrue("requestId :" + requestId + " lock failed", lock);
    }


    @Test
    public void testUnlock(){
        String key = "DistributedKey-1";
        String requestId = UUID.randomUUID().toString();
        boolean lock = new DistributedKey().lock(key, requestId, 10000);
        System.err.println("requestId:" + requestId);
        Assert.assertTrue("requestId :" + requestId + " lock failed", lock);

        boolean unlock = new DistributedKey().unlock(key, requestId);
        Assert.assertTrue("requestId :" + requestId + " unlock failed", unlock);


    }

    @Test
    public void testUnlockFailed(){
        String key = "DistributedKey-1";
        String requestId = UUID.randomUUID().toString();
        boolean lock = new DistributedKey().lock(key, requestId, 10000);
        System.err.println("requestId:" + requestId);
        Assert.assertTrue("requestId :" + requestId + " lock failed", lock);

        requestId = UUID.randomUUID().toString();
        boolean unlock = new DistributedKey().unlock(key, requestId);
        Assert.assertTrue("requestId :" + requestId + " unlock failed", unlock);

    }


    @Test
    public void testLua(){
        String script = "return {KEYS[1],KEYS[2],ARGV[1],ARGV[2]} ";
        Object result = jedis.eval(script, 2,new String[]{"key1","key2","arg1","arg2"});
        System.err.println("result:"+result);
    }




    public boolean lock(String key, String requestId, int expireTime) {
        String result = jedis.set(key, requestId, SET_IF_NOT_EXIT, SET_WITH_EXPIRE_TIME, expireTime);
        return SUCCESS.equals(result);
    }

    public boolean unlock(String key, String requestId) {
        String script = "if redis.call('get',KEYS[1]) == ARGV[1] then return redis.call('del',KEYS[1]) else return 0 end ";
        Object result = jedis.eval(script, Collections.singletonList(key), Collections.singletonList(requestId));
        return RELEASE_SUCCESS.equals(result);
    }

}
