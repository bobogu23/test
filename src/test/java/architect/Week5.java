package architect;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.*;

/**
 * 用你熟悉的编程语言实现一致性 hash 算法。
 * 编写测试用例测试这个算法，测试 100 万 KV 数据，10 个服务器节点的情况下，
 * 计算这些 KV 数据在服务器上分布数量的标准差，以评估算法的存储负载不均衡性。
 * @author:ben.gu
 * @Date:2020/7/8 11:21 下午
 */
public class Week5 {

    public static void main(String[] args) {
        TreeMap<Long, String> circular = new TreeMap<>();

        for (int i = 1; i <= 10; i++) {
            String node = "node" + i;
            circular.put(hash(node), node);

            for (int j = 0; j < 100; j++) {
                circular.put(hash(node + j), node);
            }
        }
        Map<String, Long> dataNodeCount = new TreeMap<>();

        String dataKVPrefix = "dataKVPrefix";

        for (int i = 0; i < 1000000; i++) {
            String key = dataKVPrefix + new Random().nextInt();

            Map.Entry<Long, String> entry = circular.ceilingEntry(hash(key));
            //找不到节点，找最小的
            if (entry == null) {
                entry = circular.firstEntry();
            }
            String node = entry.getValue();

            Long count = dataNodeCount.get(node);
            count = count == null ? 1L : count + 1;
            dataNodeCount.put(node, count);
        }

        dataNodeCount.forEach((k, v) -> {
            System.err.println("node:" + k + ",data count:" + v);
        });

        //标准差。sqrt(((x1-x)^2 +(x2-x)^2 +......(xn-x)^2)/n)
        int x = 1000000 / 10;
        double fangchasum = 0L;

        for (Map.Entry<String, Long> e : dataNodeCount.entrySet()) {
            fangchasum = fangchasum + Math.pow(e.getValue() - x, 2);
        }

        double r = Math.sqrt(fangchasum / 10);
        System.err.println("标准差:" + r);
        /**
         * node:node1,data count:528820
         * node:node10,data count:14768
         * node:node2,data count:11310
         * node:node3,data count:22721
         * node:node4,data count:99575
         * node:node5,data count:48728
         * node:node6,data count:71055
         * node:node7,data count:34132
         * node:node8,data count:76593
         * node:node9,data count:92298
         * 标准差:146082.73671998346
         */

    }

    public static Long hash(String key) {
        return murmurhash(key.getBytes());
    }

    public static Long murmurhash(byte[] key) {

        ByteBuffer buf = ByteBuffer.wrap(key);
        int seed = 0x1234ABCD;

        ByteOrder byteOrder = buf.order();
        buf.order(ByteOrder.LITTLE_ENDIAN);

        long m = 0xc6a4a7935bd1e995L;
        int r = 47;

        long h = seed ^ (buf.remaining() * m);

        long k;
        while (buf.remaining() >= 8) {
            k = buf.getLong();

            k *= m;
            k ^= k >>> r;
            k *= m;

            h ^= k;
            h *= m;
        }

        if (buf.remaining() > 0) {
            ByteBuffer finish = ByteBuffer.allocate(8).order(ByteOrder.LITTLE_ENDIAN);
            // for big-endian version, do this first:
            // finish.position(8-buf.remaining());
            finish.put(buf).rewind();
            h ^= finish.getLong();
            h *= m;
        }

        h ^= h >>> r;
        h *= m;
        h ^= h >>> r;

        buf.order(byteOrder);
        return h;
    }

}
