package java_core.collection.map;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author:ben.gu
 * @Date:2019/3/9 3:16 PM
 */
public class MyLRUMap<K,V> extends LinkedHashMap<K,V> {
    private static final int MAX_SIZE = 4;

    public MyLRUMap() {
        super(16, 0.75F, true);
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry eldest) {
        return size() > MAX_SIZE;
    }

    public static void main(String args[]) {
        MyLRUMap<String,String> map = new MyLRUMap();
        map.put("a","1");
        map.put("c","2");
        map.put("d","3");
        map.put("e","4");
        map.get("a");
        map.get("c");
        System.err.println(map);
        map.put("f","5");

        System.err.println(map);
    }
}
