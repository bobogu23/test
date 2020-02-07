package algorith.tree;

import java.util.Arrays;

import static algorith.heap.HeapTest.heapify;

/**
 * 堆。
 * 特点
 * 1.完全二叉树
 * 2.堆中每个节点的值都必须大于等于（或者小于等于）子树中每个节点的值
 * @author:ben.gu
 * @Date:2020/2/7 1:13 PM
 */
public class HeapTest {

    public static void main(String args[]) {
        BigTopHeap h = new BigTopHeap(8);
//        h.insert(100);
//        h.insert(1);
//        h.insert(2);
//        h.insert(49);
//        h.insert(50);
//        h.insert(30);
//        h.insert(20);
//        h.insert(200);
//        h.print();
//        h.removeTop();
//        h.print();

        int[] b = new int[] { -1, 100, 20, 30, 3, 40, 5, 90 };
        h.sort(b, 7);
        System.err.println("b:" + Arrays.toString(b));

//        System.err.println("--------------");
//
//        MyPriorityQueue queue = new MyPriorityQueue(8);
//        queue.enqueue(1);
//        queue.enqueue(10);
//        queue.enqueue(90);
//        queue.enqueue(100);
//        queue.enqueue(40);
//        queue.enqueue(34);
//
//        queue.print();
//        System.err.println("top:" + queue.dequeue());
//        queue.print();
//        System.err.println("top:" + queue.dequeue());
//        queue.print();

    }

    static class MyPriorityQueue {
        private BigTopHeap heap;

        public MyPriorityQueue(int capacity) {
            heap = new BigTopHeap(capacity);
        }

        public void enqueue(int n) {
            heap.insert(n);
        }

        public int dequeue() {
            return heap.removeTop();
        }

        public void print() {
            heap.print();
        }
    }

    static class BigTopHeap {
        //从下标为1的位置开始存储数据。位置k的节点的左节点下标为2k,右节点下标为 2k+1

        private int[] a;

        //容量
        private int capacity;

        //已经存储的节点数
        private int count;

        public void print() {
            StringBuilder sb = new StringBuilder();
            for (int i = 1; i <= count; i++) {
                sb.append(a[i]).append(",");
            }

            System.err.println(sb.deleteCharAt(sb.length() - 1).toString());
        }

        public BigTopHeap(int capacity) {
            this.a = new int[capacity + 1];
            this.capacity = capacity;
            this.count = 0;
        }

        public void insert(int data) {
            if (count >= capacity) {
                return;
            }
            count++;
            //数组末尾存放待插入的数据
            a[count] = data;
            //从下往上开始交换,保持堆是大顶堆
            int i = count;
            while (i / 2 > 0 && a[i / 2] < data) {
                //swap
                int tmp = a[i];
                a[i] = a[i / 2];
                a[i / 2] = tmp;
                i = i / 2;
            }
        }

        /**
         * 堆排序。
         * 对数组使用堆进行排序。
         * 1.将数组构建成堆。大顶或者小顶堆。
         * 2.将堆顶元素,即数组第一个元素与数组末尾n元素交换。将1到N-1位置的元素堆化,继续将第一个元素与n-1位置的元素交换。
         *   一直到 n=1
         * @param a
         * @param n
         */
        public void sort(int[] a, int n) {
            buildHeap(a, n);
            int k = n;
            while (k > 1) {
                swap(a, 1, k);
                k--;
                buildHeap(a, k);
            }
        }

        //从1到n/2位置的元素进行堆化,n/2+1 到n位置的元素是叶子节点
        private void buildHeap(int[] a, int n) {
            for (int i = n/2; i >= 1; i--) {
                heapify2(a, n, i);
            }
        }

        private void heapify2(int[] a, int n, int i) {
            while (true) {
                int index = i;
                //左节点
                if (2 * i <= n && a[i] < a[2 * i]) {
                    index = i * 2;
                }
                //右节点. 注意: 当前节点或者左节点小于右节点,跟右节点交换.（也就是取左右节点中最大的节点）
                if (2 * i + 1 <= n && a[index] < a[2 * i + 1]) {
                    index = i * 2 + 1;
                }
                if (index == i) {
                    break;
                }
                swap(a, index, i);
                i = index;
            }

        }

        /**
         * 移除堆顶元素。
         * 为了保持大顶堆的特性。将数据最后一个元素替换成堆顶元素,从堆顶开始与子节点比较,判断是否大于子节点的值
         */
        public int removeTop() {
            if (count == 0) {
                return -1;
            }
            int top = a[1];
            a[1] = a[count];
            count--;
            int i = 1;
            while (i < count / 2) {
                int index = i;
                if (a[i] < a[i * 2]) {
                    swap(a, i, i * 2);
                    i = i * 2;
                } else if (a[i] < a[i * 2 + 1]) {
                    swap(a, i, i * 2 + 1);
                    i = i * 2 + 1;
                }
                if (index == i) {
                    break;
                }

            }
            return top;
        }

        private void swap(int[] a, int i, int i1) {
            int tmp = a[i];
            a[i] = a[i1];
            a[i1] = tmp;
        }
    }

}
