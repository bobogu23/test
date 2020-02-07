package algorith.heap;

import com.alibaba.fastjson.JSON;
import javafx.util.Pair;

/**
 * 堆:
 * 一种完全二叉树。堆中每个节点都必须大于等于或者小于等于子树的节点
 * 分为大顶堆，小顶堆
 *
 * @author:ben.gu
 * @Date:2019/7/30 11:36 PM
 */
public class HeapTest {

    public static void main(String args[]) {
        //        Heap heap = new Heap(10);
        //        heap.insert(1);
        //        heap.insert(10);
        //        heap.insert(2);
        //        heap.insert(100);
        //        heap.insert(5);
        //        System.err.println("heap:" + JSON.toJSONString(heap.getA()));
        //        heap.removeTop();
        //        System.err.println("heap:" + JSON.toJSONString(heap.getA()));

        int[] a = { 0, 1, 2, 3, 4, 5, 10, 220, 9, 49, -1, 3, 56 };
        buildHeap(a, 12);
        System.err.println("heap a:" + JSON.toJSONString(a));
        sort(a,12);
        System.err.println("heap a:" + JSON.toJSONString(a));

    }

    //排序
    //堆建好后,将堆顶元素放到第n个位置（最后一位）, 1 到 n-1 的元素重新堆化，然后再取堆顶元素放到n-1的位置 直到n=1
    //数组a的元素放在1到n的位置
    public  static void sort(int[] a, int n) {
        buildHeap(a, n);
        int index = n;
        while (index > 1) {
            swap(a, index, 1);
            index--;
            heapify(a, index,1);
        }

    }

    //n：数组元素个数
    //从第一个非叶子节点,n/2 开始 从下往上堆化
    public static void buildHeap(int[] a, int n) {
        for (int i = n / 2; i >= 1; i--) {
            heapify(a, n, i);
        }
    }

    public static void heapify(int[] a, int count, int i) {
        while (true) {
            int maxPos = i;
            if (i * 2 <= count && a[i] < a[i * 2]) {
                maxPos = i * 2;
            }
            if (i * 2 + 1 <= count && a[i] < a[i * 2 + 1]) {
                maxPos = i * 2 + 1;
            }
            if (maxPos == i) {
                break;
            }
            swap(a, i, maxPos);
            i = maxPos;
        }

    }

    public static void swap(int[] a, int i, int i1) {
        int temp = a[i];
        a[i] = a[i1];
        a[i1] = temp;
    }

    static class Heap {
        //数组存储数据
        private int[] a;

        //可以存储的数据最大个数
        private int n;

        //已经使用的个数
        private int count;

        public Heap(int n) {
            this.n = n;
            this.count = 0;
            a = new int[n + 1];
        }

        //插入
        //堆化。从下往上比较，满足大于等于或者小于等于子树节点的规则
        public void insert(int data) {
            if (count >= n) {
                return;
            }
            count++;
            a[count] = data;
            int i = count;

            while (i / 2 > 0 && a[i] > a[i / 2]) {
                swap(a, i, i / 2);
                i = i / 2;
            }

        }

        // 删除
        //删除堆顶元素后，将最后一个元素放到堆顶 然后堆化

        public void removeTop() {
            if (count == 0) {
                return;
            }
            //堆顶元素用最后一个元素替换
            a[1] = a[count];
            count--;
            heapify(a, count, 1);
        }

        private void heapify(int[] a, int count, int i) {
            while (true) {
                int maxPos = i;
                if (i * 2 <= count && a[i] < a[i * 2]) {
                    maxPos = i * 2;
                }
                if (i * 2 + 1 <= count && a[maxPos] < a[i * 2 + 1]) {
                    maxPos = i * 2 + 1;
                }
                if (maxPos == i) {
                    break;
                }
                swap(a, i, maxPos);
                i = maxPos;
            }

        }

        private void swap(int[] a, int i, int i1) {
            int temp = a[i];
            a[i] = a[i1];
            a[i1] = temp;
        }

        public int[] getA() {
            return a;
        }
    }

}
