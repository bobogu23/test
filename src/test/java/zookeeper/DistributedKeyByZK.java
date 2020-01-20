package zookeeper;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 原理 在zk中创建一个节点做为共享资源，zk 客户端以创建子节点的形式占用共享资源,实现获取锁
   1. 创建持久化节点 a
   2. 在a下面创建临时顺序节点
   3. 判断当前节点在a下面的所有子节点中是否序号最小,即排第一个.如果是,说明优先抢占共享资源,拿到锁。如果不是,监听序号比当前节点小的节点。
   4. 如果当前节点监听到了节点删除事件,判断当前节点是否是序号最小节点,如果是 获取锁成功,否则再次监听序号比当前节点小的节点。

   FAQ:
   1.为什么要监听序号比当前节点小的节点？
   A:如果只监听父节点,也可以实现功能。但是如果zk 客户端很多的话,zk 服务端要给每个客户端发送节点删除相关事件,每个zk客户端判断当前节点是否是序号最小节点，如果不是要重新注册监听.产生所谓的羊群效应（herd effect)
     实际上最终获取到锁的只有一个节点,.监听一个子节点,zk 服务端只需发送释放锁的节点的删除事件,提高性能。
 * @author:ben.gu
 * @Date:2020/1/19 10:58 AM
 */
public class DistributedKeyByZK {
    private Logger logger = LoggerFactory.getLogger(getClass());

    private String zkAddress;

    private String lockName;

    private ZooKeeper zk;

    private String subNodeName;

    private static final String SUB_NODE_SUFFIX = "-lock-";

    private static final String SLASH = "/";

    private ThreadLocal<String> lockNodePath = new ThreadLocal<>();

    private static final NodePathComparator NODE_PATH_COMPARATOR = new NodePathComparator();

    public DistributedKeyByZK(String zkAddress, String lockName) {
        this.zkAddress = zkAddress;
        this.lockName = lockName.startsWith(SLASH) ? lockName : SLASH + lockName;

        CountDownLatch latch = new CountDownLatch(1);
        try {
            zk = new ZooKeeper(zkAddress, 5000, new ZKWatcher(latch, this));
        } catch (IOException e) {
            logger.error("create ZooKeeper client error:", e);
        }
        try {
            latch.await();
        } catch (InterruptedException e) {
            logger.error("wait create ZooKeeper client error:", e);
        }

        try {
            if (zk.exists(this.lockName, false) == null) {
                zk.create(this.lockName, null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }
        } catch (Exception e) {
            logger.error("create node error:", e);
        }

        this.subNodeName = UUID.randomUUID().toString().replace("-", "") + SUB_NODE_SUFFIX;

    }

    //测试
    public static void main(String args[]) {

        //模拟10个线程同时请求锁
        //每个线程睡眠随机时间模拟执行业务代码
        int parallel = 10;
        CountDownLatch signal = new CountDownLatch(1);

        CountDownLatch finish = new CountDownLatch(parallel);
        DistributedKeyByZK key = new DistributedKeyByZK("localhost:2181", "/distributedKeyTest");

        for (int i = 0; i < parallel; i++) {
            new Thread(new DistributedKeyTestTask(key, signal, finish)).start();
        }
        signal.countDown();
        try {
            finish.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    static class DistributedKeyTestTask implements Runnable {

        private Logger logger = LoggerFactory.getLogger(getClass());

        private DistributedKeyByZK key;

        private CountDownLatch signal;

        private CountDownLatch finish;

        public DistributedKeyTestTask(DistributedKeyByZK key, CountDownLatch signal, CountDownLatch finish) {
            this.key = key;
            this.signal = signal;
            this.finish = finish;
        }

        @Override
        public void run() {
            try {
                signal.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            logger.info("thread name:{} try to get lock.", Thread.currentThread().getName());
            key.lock();

            int initalNum = 2000;
            double random = Math.random();
            initalNum = (int) (initalNum + 10000 * random);
            logger.info("thread name:{} get lock. sleep time:{}", Thread.currentThread().getName(), initalNum);
            try {
                Thread.sleep(initalNum);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            logger.info("thread name:{} finish sleep,try unlock", Thread.currentThread().getName());
            key.unlock();
            finish.countDown();

        }
    }

    public void lock() {
        try {
            String path = zk.create(this.lockName + SLASH + subNodeName, null, ZooDefs.Ids.OPEN_ACL_UNSAFE,
                    CreateMode.EPHEMERAL_SEQUENTIAL);
            //判断当前节点是否是序号最小的节点
            List<String> children = zk.getChildren(lockName, false);
            if (children != null && children.size() == 1) {
                //只有当前一个节点,取锁成功.保存当前节点路径,解锁使用
                logger.info("thread name:{}, current path:{}, get lock", Thread.currentThread().getName(), path);
                lockNodePath.set(path);
                return;
            }
            //监听比当前节点序号小的 序号最大的节点
            TreeSet<String> childNodeSequenceSet = new TreeSet<>(NODE_PATH_COMPARATOR);
            childNodeSequenceSet.addAll(children);

            String lowerNodePath = childNodeSequenceSet.lower(path);
            //判断是否存在
            //e0374369d319470e9d6d14ec029cad2f-lock-0000000018
            while (lowerNodePath != null && zk.exists(this.lockName + SLASH + lowerNodePath, false) == null) {
                lowerNodePath = childNodeSequenceSet.lower(lowerNodePath);
            }
            //不存在比当前节点序号小的节点，认为当前节点获取锁成功
            if (lowerNodePath == null) {
                logger.info("thread name:{}, current path:{}, get lock", Thread.currentThread().getName(), path);
                lockNodePath.set(path);
                return;
            }
            CountDownLatch latch = new CountDownLatch(1);
            //监听节点
            final String threadName = Thread.currentThread().getName();
            zk.exists(this.lockName + SLASH + lowerNodePath, new Watcher() {
                @Override
                public void process(WatchedEvent event) {
                    logger.info("thread name:{},predecessor node path:{} has deleted", threadName, event.getPath());
                    if (event.getType() == Event.EventType.NodeDeleted) {
                        latch.countDown();
                    }
                }
            });
            //wait
            latch.await();
            lockNodePath.set(path);

        } catch (Exception e) {
            logger.error("create lock node error:", e);
            throw new RuntimeException("create lock error");
        }

    }

    public void unlock() {
        logger.info("thread name:{}, current path:{}, unlock", Thread.currentThread().getName(),
                lockNodePath.get());
        try {
            zk.delete(lockNodePath.get(), -1);
            lockNodePath.remove();
        } catch (Exception e) {
            logger.error("zk unlock error:", e);
        }

    }

    static class NodePathComparator implements Comparator<String> {

        @Override
        public int compare(String s1, String s2) {
            //1a61b0b7b4dd49c687f9589630f73fa3-lock-0000000006
            long num1 = Long.parseLong(s1.substring(s1.lastIndexOf("-") + 1));
            long num2 = Long.parseLong(s2.substring(s2.lastIndexOf("-") + 1));
            return Math.toIntExact(num1 - num2);
        }
    }

    static class ZKWatcher implements Watcher {
        private Logger logger = LoggerFactory.getLogger(ZKWatcher.class);

        private CountDownLatch latch;

        private DistributedKeyByZK distributedKeyByZK;

        public ZKWatcher(CountDownLatch latch, DistributedKeyByZK distributedKeyByZK) {
            this.latch = latch;
            this.distributedKeyByZK = distributedKeyByZK;
        }

        @Override
        public void process(WatchedEvent event) {
            if (event.getState() == Event.KeeperState.SyncConnected) {
                if (event.getType() == Event.EventType.None && event.getPath() == null) {
                    latch.countDown();
                }
            }
            //断开重连
            else if (event.getState() == Event.KeeperState.Disconnected) {
                CountDownLatch latch = new CountDownLatch(1);
                try {
                    distributedKeyByZK.zk = new ZooKeeper(distributedKeyByZK.zkAddress, 5000,
                            new ZKWatcher(latch, distributedKeyByZK));
                } catch (IOException e) {
                    logger.error("reconnect zk server error:", e);
                }
                try {
                    latch.await();
                } catch (InterruptedException e) {
                    logger.error("wait reconnect zk server error:", e);
                }
            }

        }
    }

}
