package jvm;

/**
 * @author:ben.gu
 * @Date:2019/12/8 12:16 PM
 */
public class GCExercise1 {

    /**
     * * JVM Parameter
     * -XX:NewSize=100M
     * -XX:MaxNewSize=100M
     * -XX:InitialHeapSize=200M
     * -XX:MaxHeapSize=200M
     * -XX:SurvivorRatio=8
     * -XX:MaxTenuringThreshold=15
     * -XX:PretenureSizeThreshold=10M
     *-XX:+UseParNewGC
     * -XX:+PrintGCDetails
     -XX:+PrintGCDateStamps
     -Xloggc:/Users/xmly/code/test/test/src/test/java/jvm/gc_exercsise1.log
     */

    /**
     *   -XX:NewSize=100M
     -XX:MaxNewSize=100M
     -XX:InitialHeapSize=200M
     -XX:MaxHeapSize=200M
     -XX:SurvivorRatio=8
     -XX:MaxTenuringThreshold=15
     -XX:PretenureSizeThreshold=10M
     -XX:+UseParNewGC
     -XX:+PrintGCDetails
     -XX:+PrintGCDateStamps
     -Xloggc:/Users/xmly/code/test/test/src/test/java/jvm/gc_exercsise1.log
     */

    public static void main(String args[]) throws InterruptedException {
        Thread.sleep(30000);

        while (true) {
            loadData();
        }

    }

    private static void loadData() throws InterruptedException {
        byte[] data = null;
        for (int i = 0; i < 50; i++) {
            data = new byte[1024 * 100];
        }
        data = null;
        Thread.sleep(1000);

    }

}
