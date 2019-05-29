package bloomfilter;

import com.google.common.collect.Lists;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

import java.util.List;

/**
 * 不存在的可能判断为存在 误报
 * 已存在的肯定判断为存在
 * 可以使用redis的bitmap 实现分布式bloom 过滤器
 * @author:ben.gu
 * @Date:2019/5/29 5:14 PM
 */
public class BloomFilterTest {
    private static int size = 1000000;

    private static BloomFilter<Integer> filter = BloomFilter.create(Funnels.integerFunnel(), size,0.0001);

    public static void main(String args[]) {
        for (int i = 0; i < size; i++) {
            filter.put(i);
        }

        for (int i = 0; i < size; i++) {
            if (!filter.mightContain(i)) {
                System.err.println("不在过滤器内,误报了...");
            }
        }

        List<Integer> list = Lists.newArrayList(1000);
        for (int i = size + 10000; i < size + 20000; i++) {
            if (filter.mightContain(i)) {
                list.add(i);
            }
        }

        System.err.println("明明不在过滤器内,误报了...,数量:"+list.size());

    }
}
