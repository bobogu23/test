package jvm;

/**
 * @author:ben.gu
 * @Date:2019/12/8 9:14 PM
 */
public class GCExercise2 {

    /**

     -XX:NewSize=100M
     -XX:MaxNewSize=100M
     -XX:InitialHeapSize=200M
     -XX:MaxHeapSize=200M
     -XX:SurvivorRatio=8
     -XX:MaxTenuringThreshold=15
     -XX:PretenureSizeThreshold=20M
     -XX:+UseParNewGC
     -XX:+UseConcMarkSweepGC
     -XX:+PrintGCDetails
     -XX:+PrintGCDateStamps
     -Xloggc:/Users/xmly/code/test/test/src/test/java/jvm/gc_exercsise2.log
     */


    /**

     -XX:NewSize=200M
     -XX:MaxNewSize=200M
     -XX:InitialHeapSize=300M
     -XX:MaxHeapSize=300M
     -XX:SurvivorRatio=2
     -XX:MaxTenuringThreshold=15
     -XX:PretenureSizeThreshold=20M
     -XX:+UseParNewGC
     -XX:+UseConcMarkSweepGC
     -XX:+PrintGCDetails
     -XX:+PrintGCDateStamps
     -Xloggc:/Users/xmly/code/test/test/src/test/java/jvm/gc_exercsise2.log
     */

    public static void main(String args[]) throws InterruptedException {
        Thread.sleep(30000);

        while (true) {
            loadData();
        }

    }

    private static void loadData() throws InterruptedException {
        byte[] data = null;
        for (int i = 0; i < 4; i++) {
            data = new byte[1024 * 1024*10];
        }
        data = null;
        byte[] data1 = new byte[1024 * 1024*10];
        byte[] data2 = new byte[1024 * 1024*10];
        byte[] data3 = new byte[1024 * 1024*10];
        data3 = new byte[1024 * 1024*10];

        Thread.sleep(1000);

    }



}
