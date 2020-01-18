package netty.simplerpcframework.core.registry.zk;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 服务注册.将serviceName 与IP,端口的 映射关系注册到zk
 * @author:ben.gu
 * @Date:2020/1/13 5:35 PM
 */
public class ServiceRegistry {
    private Logger logger = LoggerFactory.getLogger(getClass());

    private String zkAdress;

    private CountDownLatch cdl = new CountDownLatch(1);

    private ZooKeeper zk;

    private static String ROOT_PATH = "/simpleRpc";

    private static String SLASH = "/";

    private static ServiceRegistry serviceRegistry;

    private ServiceRegistry(String zkAdress) {
        this.zkAdress = zkAdress;
        this.zk = connectServer();
        Objects.requireNonNull(zk);
        try {
            if (zk.exists(ROOT_PATH, false) == null) {
                zk.create(ROOT_PATH, null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static ServiceRegistry getInstance(String zkAdress) {
        if (serviceRegistry == null) {
            synchronized (ServiceRegistry.class) {
                if (serviceRegistry == null) {
                    serviceRegistry = new ServiceRegistry(zkAdress);
                }
            }
        }
        return serviceRegistry;
    }

    private ZooKeeper connectServer() {
        ZooKeeper zk = null;
        ZkWatcher zkWatcher = new ZkWatcher(cdl);
        try {
            zk = new ZooKeeper(zkAdress, 5000, zkWatcher);
        } catch (IOException e) {
            logger.error("connect zk error:", e);
        }
        try {
            cdl.await();
        } catch (InterruptedException e) {
            logger.error("zk connect wait error:", e);
        }
        return zk;
    }

    /**
     * 注册服务,地址
     * @param serviceName
     * @param address
     */
    public void createServiceNameAddressNode(String serviceName, String address) {
        try {
            if (zk.exists(ROOT_PATH + SLASH + serviceName, false) == null) {
                String path = zk.create(ROOT_PATH + SLASH + serviceName, null, ZooDefs.Ids.OPEN_ACL_UNSAFE,
                        CreateMode.PERSISTENT);
                logger.info("node:{},actual path:{}", serviceName, path);
            }
            String path = zk.create(ROOT_PATH + SLASH + serviceName + SLASH + address, null,
                    ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
            logger.info("node:{},actual path:{}", serviceName + SLASH + address, path);
        } catch (Exception e) {
            logger.error("createServiceNameAddressNode error:", e);
        }
    }

    private static class ZkWatcher implements Watcher {
        private CountDownLatch cdl;

        public ZkWatcher(CountDownLatch cdl) {
            this.cdl = cdl;
        }

        @Override
        public void process(WatchedEvent watchedEvent) {
            if (Event.KeeperState.SyncConnected == watchedEvent.getState()) {
                if (Event.EventType.None == watchedEvent.getType() && watchedEvent.getPath() == null) {
                    cdl.countDown();
                }

            }
        }

    }

}
