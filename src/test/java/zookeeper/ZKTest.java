package zookeeper;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;

/**
 * @author:ben.gu
 * @Date:2019/7/28 4:21 PM
 */
public class ZKTest {

    static class ZooKeeperProSync implements Watcher {

        private static CountDownLatch connectedSemaphore = new CountDownLatch(1);

        private static ZooKeeper zk = null;

        private static Stat stat = new Stat();

        private static String path = "/username";

        public static void main(String args[]) throws Exception {

            zk = new ZooKeeper("localhost:2181", 5000, new ZooKeeperProSync());
            //等待zk 连接成功
            connectedSemaphore.await();

            if (zk.exists(path, true) == null) {
                zk.create(path, "hello".getBytes(Charset.forName("utf-8")), ZooDefs.Ids.OPEN_ACL_UNSAFE,
                        CreateMode.PERSISTENT);
            }

            System.err.println(new String(zk.getData(path, true, stat)));

            //            if(zk.exists(path+"/11",true) == null){
            //                zk.create(path+"/11",null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
            //            }
            //
            //            List<String> nodes = zk.getChildren(path, true);
            //
            //            System.err.println("path:"+path+",child nodes:"+ JSON.toJSONString(nodes));

            Thread.sleep(1000000);

        }

        private void setChildNodes(String path, Map<String, List<String>> map)
                throws KeeperException, InterruptedException {

            if (!path.startsWith("/")) {
                path = "/" + path;
            }

            if (zk.exists(path, true) != null) {
                List<String> children = zk.getChildren(path, true);
                if (!CollectionUtils.isEmpty(children)) {
                    map.put(path, children);
                    for (String childNode : children) {
                        if(path.endsWith("/")){
                            path = path.substring(0,path.length()-1);
                        }
                        setChildNodes(path + "/" + childNode, map);
                    }
                }
            }

        }

        @Override
        public void process(WatchedEvent watchedEvent) {

            System.err.println("event type:" + watchedEvent.getType() + ",path:" + watchedEvent.getPath());

            try {

                zk.getChildren("/simpleRpc", true);

                Map<String, List<String>> nodeChildsMap = new HashMap<>();
                //不能从 根节点开始遍历,如果数据量很大,消耗性能
                setChildNodes("/simpleRpc", nodeChildsMap);

                System.err.println("nodeChildsMap:" + JSON.toJSONString(nodeChildsMap));
            } catch (KeeperException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (Event.KeeperState.SyncConnected == watchedEvent.getState()) {
                if (Event.EventType.None == watchedEvent.getType() && watchedEvent.getPath() == null) {
                    connectedSemaphore.countDown();
                } else if (watchedEvent.getType() == Event.EventType.NodeDataChanged) {
                    try {
                        System.err.println("配置修改，新值:" + new String(zk.getData(watchedEvent.getPath(), true, stat)));
                    } catch (KeeperException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }

        }
    }

}
