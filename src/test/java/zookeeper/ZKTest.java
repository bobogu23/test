package zookeeper;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * @author:ben.gu
 * @Date:2019/7/28 4:21 PM
 */
public class ZKTest {

    static class ZooKeeperProSync implements Watcher {

        private static CountDownLatch connectedSemaphore = new CountDownLatch(1);

        private static ZooKeeper zk = null;

        private static Stat stat = new Stat();

        public static void main(String args[]) throws Exception {
            String path = "/username";
            zk = new ZooKeeper("localhost:2181", 5000, new ZooKeeperProSync());
            //等待zk 连接成功
            connectedSemaphore.await();

            System.err.println(new String(zk.getData(path, true, stat)));

            Thread.sleep(100000);

        }

        @Override
        public void process(WatchedEvent watchedEvent) {
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
