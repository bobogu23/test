package netty.simplerpcframework.core.registry.zk;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.CountDownLatch;

import com.google.common.collect.Lists;
import org.apache.zookeeper.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

/**
 * 服务注册.将serviceName 与IP,端口的 映射关系注册到zk
 * @author:ben.gu
 * @Date:2020/1/13 5:35 PM
 */
public class ServiceDiscovery implements Watcher {
    private Logger logger = LoggerFactory.getLogger(getClass());

    private String zkAdress;

    private CountDownLatch cdl;

    private ZooKeeper zk;

    private ServiceNodeCallBack callBack;

    private static String ROOT_PATH = "/simpleRpc";

    private static String SLASH = "/";

    private static ServiceDiscovery serviceRegistry;

    private ServiceDiscovery(String zkAdress, ServiceNodeCallBack callBack) {
        this.zkAdress = zkAdress;
        this.callBack = callBack;
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

    public static ServiceDiscovery getInstance(String zkAdress, ServiceNodeCallBack callBack) {
        if (serviceRegistry == null) {
            synchronized (ServiceDiscovery.class) {
                if (serviceRegistry == null) {
                    serviceRegistry = new ServiceDiscovery(zkAdress, callBack);
                }
            }
        }
        return serviceRegistry;
    }

    private ZooKeeper connectServer() {
        ZooKeeper zk = null;
        this.cdl = new CountDownLatch(1);
        try {
            zk = new ZooKeeper(zkAdress, 5000, this);
        } catch (IOException e) {
            logger.error("connect zk error:", e);
        }
        try {
            this.cdl.await();
        } catch (InterruptedException e) {
            logger.error("zk connect wait error:", e);
        }
        return zk;
    }

    /**
     * serviceName,地址 映射
     * @return serviceName,地址 映射
     */
    public Map<String, List<String>> getNodeChildMap() {
        Map<String, List<String>> nodeChildMap = new HashMap<>();
        try {
            getNodeInfo(ROOT_PATH, nodeChildMap);
        } catch (Exception e) {
            logger.error("get zk node info error:", e);
        }
        return nodeChildMap;
    }

    @Override
    public void process(WatchedEvent event) {

        String path = event.getPath();

        if (Event.KeeperState.SyncConnected == event.getState()) {
            if (Event.EventType.None == event.getType() && path == null) {
                cdl.countDown();
                return;
            }
            try {
                //监听节点 删除,新增事件
                if (event.getType() == Event.EventType.NodeDeleted
                        || event.getType() == Event.EventType.NodeChildrenChanged) {

                    logger.info("watch event:{},path:{}", event.getType(), event.getPath());
                    //节点发生变化触发回调
                    Map<String, List<String>> nodeChildsMap = new HashMap<>();
                    if (event.getType() == Event.EventType.NodeDeleted) {
                        String childPath = path.substring(path.lastIndexOf(SLASH) + 1);
                        String parentPath = path.substring(ROOT_PATH.length() + 1, path.lastIndexOf(SLASH));
                        nodeChildsMap.put(parentPath, Lists.newArrayList(childPath));
                        this.callBack.callBack(2, nodeChildsMap);
                    } else {
                        getNodeInfo(path, nodeChildsMap);
                        this.callBack.callBack(1, nodeChildsMap);
                    }
                }
                //zk 监听只会触发一次，触发后,重新监听.
                watchChild(ROOT_PATH);
            } catch (Exception e) {
                logger.error("get child node error:", e);
            }
        }
        //断开重连
        else if (Event.KeeperState.Expired == event.getState()) {
            logger.info("reconnect zk server...");
            this.zk = connectServer();
        }

    }

    private void watchChild(String path) throws KeeperException, InterruptedException {
        if (!path.startsWith(SLASH)) {
            path = SLASH + path;
        }

        if (zk.exists(path, true) != null) {
            List<String> children = zk.getChildren(path, true);
            if (!CollectionUtils.isEmpty(children)) {

                for (String childNode : children) {
                    if (path.endsWith(SLASH)) {
                        path = path.substring(0, path.length() - 1);
                    }
                    watchChild(path + SLASH + childNode);
                }
            }
        }
    }

    private void getNodeInfo(String path, Map<String, List<String>> map)
            throws KeeperException, InterruptedException {

        if (!path.startsWith(SLASH)) {
            path = SLASH + path;
        }

        if (zk.exists(path, false) != null) {
            List<String> children = zk.getChildren(path, false);
            if (!CollectionUtils.isEmpty(children)) {
                //如果是/simpleRpc/ 开头,去掉/simpleRpc/.默认zk 树状结构
                /**                 simpleRpc
                 *         /           \              \
                 *   serviceName1      serviceName2   serviceName3 ......
                 *       /              \              \
                 *  address             address        address    .......
                 *
                 */
                if (path.startsWith("/simpleRpc/")) {
                    map.put(path.substring(path.lastIndexOf(SLASH) + 1), children);
                }
                for (String childNode : children) {
                    if (path.endsWith(SLASH)) {
                        path = path.substring(0, path.length() - 1);
                    }
                    getNodeInfo(path + SLASH + childNode, map);
                }
            }
        }

    }

}
