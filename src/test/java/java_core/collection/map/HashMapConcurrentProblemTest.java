package java_core.collection.map;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author:ben.gu
 * @Date:2019/3/18 8:26 PM
 */
public class HashMapConcurrentProblemTest {

    private static  Map<Integer,Integer> map =new HashMap<Integer, Integer>(2);

    public static  void putData(){
        map.put(1,1);
        map.put(1,2);
        map.put(1,3);
        map.put(1,5);
        map.put(1,7);
        map.put(1,11);
        map.put(1,9);
    }

    @Test
    public void test(){

        new Thread(new Runnable() {
            @Override public void run() {
                putData();
            }
        }).start();
        new Thread(new Runnable() {
            @Override public void run() {
                putData();
            }
        }).start();


        for(Integer key:map.keySet()){
            System.err.println("key:"+key+"value:"+map.get(key));
        }


    }
}
