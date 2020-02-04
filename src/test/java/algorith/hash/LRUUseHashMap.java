package algorith.hash;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 使用hashmap实现 LRU
 * @author:ben.gu
 * @Date:2020/2/4 2:00 PM
 */

public class LRUUseHashMap {

    public static void main(String args[]) {
        MyLRU<String,String> cache = new MyLRU(5);
        cache.put("1","a");
        cache.put("2","a");
        cache.put("3","a");
        cache.put("4","a");
        System.err.println(cache.toString());
        cache.put("5","a");

        System.err.println(cache.toString());
        cache.put("6","a");
        System.err.println(cache.toString());
        cache.put("7","1");
        System.err.println(cache.toString());
        cache.get("3");
        cache.put("8","x");
        System.err.println(cache.toString());

        cache.get("3");
        cache.get("5");
        cache.put("8","xxa");

        System.err.println(cache.toString());


    }

    static class MyLRU<K,V> {

        private int capacity;

        private LinkedHashMap map;

        public MyLRU(int capacity) {
            this.capacity = capacity;
            //exceed capacity remove eldest
            map = new LinkedHashMap(capacity, 0.75f,true) {
                @Override
                protected boolean removeEldestEntry(Map.Entry eldest) {
                    return this.size() > capacity;
                }
            };
        }

        public void put(K t,V v) {
            map.put(t,v);
        }

        public void get(K t) {
            map.get(t);
        }

        @Override public String toString() {
            return "MyLRU{" + "capacity=" + capacity + ", map=" + map.toString() + '}';
        }
    }

}
