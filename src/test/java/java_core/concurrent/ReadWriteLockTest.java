package java_core.concurrent;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读写锁测试
 * @author:ben.gu
 * @Date:2019/4/24 9:49 PM
 */
public class ReadWriteLockTest {

    class Cache<K, V> {
        private Map<K, V> m = new HashMap<>();

        private ReadWriteLock rwl = new ReentrantReadWriteLock();

        private Lock r = rwl.readLock();

        private Lock w = rwl.writeLock();

        public V get(K key) {
            r.lock();
            try {
                return m.get(key);
            } finally {
                r.unlock();
            }

        }

        public V put(K key, V value) {
            w.lock();
            try {
                return m.put(key, value);
            } finally {
                w.unlock();
            }
        }

    }

}
