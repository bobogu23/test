package jvm;

/**
 * java version "1.8.0_171"
 Java(TM) SE Runtime Environment (build 1.8.0_171-b11)
 Java HotSpot(TM) 64-Bit Server VM (build 25.171-b11, mixed mode)
 * @author:ben.gu
 * @Date:2019/12/6 9:48 AM
 */
public class GCLogAnalyze {

    /**
     * JVM Parameter
     * -XX:NewSize=10M
     * -XX:MaxNewSize=10M
     * -XX:InitialHeapSize=20M
     * -XX:MaxHeapSize=20M
     * -XX:SurvivorRatio=8
     * -XX:MaxTenuringThreshold=15
     * -XX:PretenureSizeThreshold=10M
     *-XX:+UseParNewGC
     * -XX:+PrintGCDetails
     -XX:+PrintGCDateStamps
     -Xloggc:/Users/xmly/code/test/test/src/test/java/jvm/gc.log
     * @param args
     */
    public static void main(String args[]) {

//        testYoungGc();
        testMaxTenuringThreshold();
    }

    public static void testYoungGc(){
        byte[] s1= new byte[1024*1024*2];
        s1= new byte[1024*1024*2];
        s1= new byte[1024*1024*2];
        s1= null;

        byte[] s2= new byte[1024*128];
        byte[] s3= new byte[1024*1024*2];

        /**
         * 2019-12-06T09:58:35.860-0800: 0.153: [GC (Allocation Failure)
         2019-12-06T09:58:35.860-0800: 0.153: [ParNew: 7303K->523K(9216K), 0.0014035 secs] 7303K->523K(19456K), 0.0015207 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
         [ParNew: 7303K->523K(9216K), 0.0014035 secs] :回收前7303K,回收后523K(其他对象) 年轻代可用空间9216K,SurvivorRatio=8,Eden+一个Survivor=90%年轻代.10*1024*0.9=9216
         7303K->523K(19456K), 0.0015207 secs.整个堆内存使用情况.1024*10+10*1024*0.9=19456


         ####下面是jvm退出时打印的当前堆内存使用情况
         Heap
         par new generation   total 9216K, used 2653K [0x00000007bec00000, 0x00000007bf600000, 0x00000007bf600000)
         年轻代可用空间9216K，已使用2653K。1024*128+1024*1024*2=2228K。还有一些其他对象
         eden space 8192K,  26% used [0x00000007bec00000, 0x00000007bee14930, 0x00000007bf400000)  2*1024+128=2176 2176/8192=26%
         from space 1024K,  51% used [0x00000007bf500000, 0x00000007bf582d28, 0x00000007bf600000) 523K其他对象 523/1024=51%
         to   space 1024K,   0% used [0x00000007bf400000, 0x00000007bf400000, 0x00000007bf500000)
         tenured generation   total 10240K, used 0K [0x00000007bf600000, 0x00000007c0000000, 0x00000007c0000000)
         the space 10240K,   0% used [0x00000007bf600000, 0x00000007bf600000, 0x00000007bf600200, 0x00000007c0000000)
         Metaspace       used 2681K, capacity 4486K, committed 4864K, reserved 1056768K
         class space    used 287K, capacity 386K, committed 512K, reserved 1048576K
         */

    }

    /**
     * 测试对象在Survivor中经过15次回收后进入老年代.保证survivor中每次young gc 后，对象占用的内存不超过50%,否则按照年龄判断超过50%的对象会进入老年代
     * JVM Parameter
     * -XX:NewSize=10M
     * -XX:MaxNewSize=10M
     * -XX:InitialHeapSize=20M
     * -XX:MaxHeapSize=20M
     * -XX:SurvivorRatio=8
     * -XX:MaxTenuringThreshold=15
     * -XX:PretenureSizeThreshold=10M
     *-XX:+UseParNewGC
     * -XX:+PrintGCDetails
     -XX:+PrintGCDateStamps
     -Xloggc:/Users/xmly/code/test/test/src/test/java/jvm/gc.log
     * @param
     */
    public static void testMaxTenuringThreshold(){
        byte[] s2 = new byte[512];

        //eden 区8M
        //每次触发younggc，15次
        for(int i =0;i<17;i++){
            byte[] s1= new byte[1024*1024*2];
            s1= new byte[1024*1024*2];
            s1= new byte[1024*1024*2];
            s1 = null;
        }

        /**
         Java HotSpot(TM) 64-Bit Server VM (25.171-b11) for bsd-amd64 JRE (1.8.0_171-b11), built on Mar 28 2018 12:50:57 by "java_re" with gcc 4.2.1 (Based on Apple Inc. build 5658) (LLVM build 2336.11.00)
         Memory: 4k page, physical 8388608k(156304k free)

         /proc/meminfo:

         CommandLine flags: -XX:InitialHeapSize=20971520 -XX:MaxHeapSize=20971520 -XX:MaxNewSize=10485760 -XX:MaxTenuringThreshold=15 -XX:NewSize=10485760 -XX:PretenureSizeThreshold=10485760 -XX:+PrintGC -XX:+PrintGCDateStamps -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -XX:SurvivorRatio=8 -XX:+UseCompressedClassPointers -XX:+UseCompressedOops -XX:+UseParNewGC
         2019-12-06T19:41:00.423-0800: 0.118: [GC (Allocation Failure) 2019-12-06T19:41:00.423-0800: 0.118: [ParNew: 7303K->531K(9216K), 0.0016189 secs] 7303K->531K(19456K), 0.0017388 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
         2019-12-06T19:41:00.426-0800: 0.120: [GC (Allocation Failure) 2019-12-06T19:41:00.426-0800: 0.120: [ParNew: 6835K->609K(9216K), 0.0017050 secs] 6835K->2657K(19456K), 0.0017627 secs] [Times: user=0.01 sys=0.00, real=0.00 secs]
         2019-12-06T19:41:00.428-0800: 0.123: [GC (Allocation Failure) 2019-12-06T19:41:00.428-0800: 0.123: [ParNew: 7059K->637K(9216K), 0.0017220 secs] 9107K->4733K(19456K), 0.0017726 secs] [Times: user=0.00 sys=0.00, real=0.01 secs]
         2019-12-06T19:41:00.430-0800: 0.125: [GC (Allocation Failure) 2019-12-06T19:41:00.430-0800: 0.125: [ParNew: 6928K->623K(9216K), 0.0010157 secs] 11024K->4719K(19456K), 0.0010533 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
         2019-12-06T19:41:00.432-0800: 0.126: [GC (Allocation Failure) 2019-12-06T19:41:00.432-0800: 0.126: [ParNew: 6918K->574K(9216K), 0.0009755 secs] 11014K->4670K(19456K), 0.0010128 secs] [Times: user=0.01 sys=0.00, real=0.00 secs]
         2019-12-06T19:41:00.433-0800: 0.128: [GC (Allocation Failure) 2019-12-06T19:41:00.433-0800: 0.128: [ParNew: 6873K->673K(9216K), 0.0017146 secs] 10969K->6817K(19456K), 0.0017774 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
         2019-12-06T19:41:00.435-0800: 0.130: [GC (Allocation Failure) 2019-12-06T19:41:00.435-0800: 0.130: [ParNew: 6974K->767K(9216K), 0.0016372 secs] 13118K->8959K(19456K), 0.0016851 secs] [Times: user=0.01 sys=0.00, real=0.00 secs]
         2019-12-06T19:41:00.437-0800: 0.132: [GC (Allocation Failure) 2019-12-06T19:41:00.437-0800: 0.132: [ParNew: 7069K->7069K(9216K), 0.0000245 secs]2019-12-06T19:41:00.437-0800: 0.132: [Tenured: 8192K->493K(10240K), 0.0014509 secs] 15261K->493K(19456K), [Metaspace: 2672K->2672K(1056768K)], 0.0015467 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
         2019-12-06T19:41:00.439-0800: 0.134: [GC (Allocation Failure) 2019-12-06T19:41:00.439-0800: 0.134: [ParNew: 6302K->0K(9216K), 0.0009251 secs] 6795K->493K(19456K), 0.0009638 secs] [Times: user=0.00 sys=0.00, real=0.01 secs]
         2019-12-06T19:41:00.441-0800: 0.135: [GC (Allocation Failure) 2019-12-06T19:41:00.441-0800: 0.135: [ParNew: 6302K->0K(9216K), 0.0008391 secs] 6796K->2541K(19456K), 0.0008859 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
         2019-12-06T19:41:00.442-0800: 0.137: [GC (Allocation Failure) 2019-12-06T19:41:00.442-0800: 0.137: [ParNew: 6303K->0K(9216K), 0.0007940 secs] 8844K->4589K(19456K), 0.0008315 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
         2019-12-06T19:41:00.443-0800: 0.138: [GC (Allocation Failure) 2019-12-06T19:41:00.443-0800: 0.138: [ParNew: 6303K->0K(9216K), 0.0008073 secs] 10892K->4589K(19456K), 0.0008610 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
         2019-12-06T19:41:00.445-0800: 0.140: [GC (Allocation Failure) 2019-12-06T19:41:00.445-0800: 0.140: [ParNew: 6303K->0K(9216K), 0.0016654 secs] 10892K->4589K(19456K), 0.0017265 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
         2019-12-06T19:41:00.447-0800: 0.142: [GC (Allocation Failure) 2019-12-06T19:41:00.447-0800: 0.142: [ParNew: 6303K->0K(9216K), 0.0008037 secs] 10892K->6637K(19456K), 0.0008499 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
         2019-12-06T19:41:00.448-0800: 0.143: [GC (Allocation Failure) 2019-12-06T19:41:00.448-0800: 0.143: [ParNew: 6303K->0K(9216K), 0.0007704 secs] 12940K->8685K(19456K), 0.0008139 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
         2019-12-06T19:41:00.450-0800: 0.144: [GC (Allocation Failure) 2019-12-06T19:41:00.450-0800: 0.144: [ParNew: 6303K->6303K(9216K), 0.0000140 secs]2019-12-06T19:41:00.450-0800: 0.144: [Tenured: 8685K->493K(10240K), 0.0010524 secs] 14988K->493K(19456K), [Metaspace: 2672K->2672K(1056768K)], 0.0011133 secs] [Times: user=0.01 sys=0.00, real=0.00 secs]
         2019-12-06T19:41:00.451-0800: 0.146: [GC (Allocation Failure) 2019-12-06T19:41:00.451-0800: 0.146: [ParNew: 6303K->0K(9216K), 0.0006966 secs] 6796K->493K(19456K), 0.0007204 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
         2019-12-06T19:41:00.453-0800: 0.147: [GC (Allocation Failure) 2019-12-06T19:41:00.453-0800: 0.147: [ParNew: 6303K->0K(9216K), 0.0008356 secs] 6796K->2541K(19456K), 0.0008858 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
         2019-12-06T19:41:00.454-0800: 0.149: [GC (Allocation Failure) 2019-12-06T19:41:00.454-0800: 0.149: [ParNew: 6303K->0K(9216K), 0.0008571 secs] 8844K->4589K(19456K), 0.0009034 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
         2019-12-06T19:41:00.455-0800: 0.150: [GC (Allocation Failure) 2019-12-06T19:41:00.455-0800: 0.150: [ParNew: 6303K->0K(9216K), 0.0013225 secs] 10892K->4589K(19456K), 0.0013641 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
         2019-12-06T19:41:00.457-0800: 0.152: [GC (Allocation Failure) 2019-12-06T19:41:00.457-0800: 0.152: [ParNew: 6303K->0K(9216K), 0.0008298 secs] 10892K->4589K(19456K), 0.0008778 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
         Heap
         par new generation   total 9216K, used 2212K [0x00000007bec00000, 0x00000007bf600000, 0x00000007bf600000)
         eden space 8192K,  27% used [0x00000007bec00000, 0x00000007bee290e0, 0x00000007bf400000)
         from space 1024K,   0% used [0x00000007bf500000, 0x00000007bf500000, 0x00000007bf600000)
         to   space 1024K,   0% used [0x00000007bf400000, 0x00000007bf400000, 0x00000007bf500000)
         tenured generation   total 10240K, used 4589K [0x00000007bf600000, 0x00000007c0000000, 0x00000007c0000000)
         the space 10240K,  44% used [0x00000007bf600000, 0x00000007bfa7b4d8, 0x00000007bfa7b600, 0x00000007c0000000)
         Metaspace       used 2678K, capacity 4486K, committed 4864K, reserved 1056768K
         class space    used 287K, capacity 386K, committed 512K, reserved 1048576K

         1803284-21140

         (2098-1690)/600

         600/360=1.67
         819/1.67=490 //490M每秒

         280 qps
         490/280=1.75M (一次请求)

         sur= 2658671-850404 = 1808267
         ygc 后 sur =1822151-13451=1808700

         晋升: 1808700-1808267 =433 kb

         sur 最小内存1600 M

         -XX:CMSInitiatingOccupancyFraction=70
         -XX:CMSFullGCsBeforeCompaction=5

         可以改成
         -XX:CMSInitiatingOccupancyFraction=80
         -XX:CMSFullGCsBeforeCompaction=0

         old generation
         1024*2=2048M
         2048*0.8=1638.4(可用内存)
         2048/0.8=2560M

         young generation
         4*1024-2560=1536M
         eden:1536*8/10=1228M (1024*8/10=819M)
         Survivor:1536/10=153.6M(1024/10=102.4M)


         1228/490=2.5s 一次





         */


    }

}
