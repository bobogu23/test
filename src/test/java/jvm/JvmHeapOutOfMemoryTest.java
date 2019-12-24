package jvm;

import java.util.ArrayList;
import java.util.List;

/**
 * @author:ben.gu
 * @Date:2019/11/23 2:25 AM
 */
public class JvmHeapOutOfMemoryTest {

    /**
    
     -XX:NewSize=8M
     -XX:MaxNewSize=8M
     -XX:InitialHeapSize=10M
     -XX:MaxHeapSize=10M
     -XX:SurvivorRatio=8
     -XX:MaxTenuringThreshold=15
     -XX:PretenureSizeThreshold=20M
     -XX:+UseParNewGC
     -XX:+UseConcMarkSweepGC
     -XX:+PrintGCDetails
     -XX:+PrintGCDateStamps
     -Xloggc:/Users/xmly/code/test/test/src/test/java/jvm/gc_exercsise3.log
     -XX:+HeapDumpOnOutOfMemoryError
     -XX:HeapDumpPath=/Users/xmly/code/test/test/src/test/java/jvm/JvmHeapOutOfMemoryTest.hprof
     */
    public static void main(String args[]) {
        List<Object> list = new ArrayList<>();
        while (true) {
            list.add(new Object());
        }
    }

}
