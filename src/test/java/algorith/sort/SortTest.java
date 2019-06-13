package algorith.sort;

import com.alibaba.fastjson.JSON;

/**
 *
 * @author:ben.gu
 * @Date:2019/6/4 9:13 PM
 */
public class SortTest {
    public static void main(String args[]) {

        //        int[] l = { 1, 2, 3, 4, 5, 6, 7, 7, -1, -2, -4, 0 };
        //        bubbleSort(l, l.length);
        //        System.err.println(JSON.toJSONString(l));
        //
        //        int[] l1 = { 1, 2, 3, 4, 5, 6, 7, 7, -1, -2, -4, 0 };
        //        insertSort(l1, l1.length);
        //        System.err.println(JSON.toJSONString(l1));

        //        int[] l2= { 1, 2, 3, 4, 5, 6, 7, 8,9,10 };
//        int[] l2 = { 10, 9, 8, 7, 6, 5, 4, 3, 2, 1 };
        int[] l2 = { 10, 9, 8, 7, 46, 53, 14, 3, 2, 100 };
        mergeSort(l2, l2.length);
        System.err.println(JSON.toJSONString(l2));

    }

    /**
     * 插入排序
     * 思想，数据分为已排序，未排序 2部分。从未排序部分取数据,在已排序数据中查找位置插入
     *
     * @param array
     * @param len
     */
    public static void insertSort(int[] array, int len) {
        if (len <= 1) {
            return;
        }

        //从0开始,第一个元素为已排序
        for (int i = 1; i < len; i++) {
            int j = i - 1;
            //未排序数
            int value = array[i];
            for (; j >= 0; j--) {
                //跟已排序的比较
                if (array[j] > value) {
                    //未排序数比已排序的小,移位
                    array[j + 1] = array[j];
                } else {
                    break;
                }
            }
            //插入value
            array[j + 1] = value;
        }
    }

    /**
     *冒泡排序
     * @param array
     * @param n 数组大小
     */
    public static void bubbleSort(int[] array, int n) {

        //排序同时统计是否有交换操作，如果没有说明已经排序完成
        if (n <= 1) {
            return;
        }

        for (int i = 0; i < n; i++) {
            boolean moved = false;
            for (int j = 0; j < n - 1 - i; j++) {
                if (array[j] > array[j + 1]) {
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                    moved = true;
                }
            }
            if (!moved) {
                System.err.println("did not move,i=" + i + ",length=" + n);
                return;
            }
        }
    }

    /**
     * 选择排序算法的实现思路有点类似插入排序，也分已排序区间和未排序区间。
     * 但是选择排序每次会从未排序区间中找到最小的元素，将其放到已排序区间的末尾
     */

    /**
     * 快速排序：
     * 如果要排序数组中下标从p到r之间的一组数据,选择p,r之间的任意一个数作为分区点pivot,遍历p,r之间的数,小于pivot的放左边,
     * 大于pivot的放右边，pivot 放中间。递归p,pivot-1,  pivot+1,r，直到区间大小为1
     *
     */

    /**
     * 归并排序：
     * 将要排序的数组分成一半,各自再分成一半排序,直到不能再分为止。排序好之后将结果排序最终得到排序好的数组
     */

    public static void mergeSort(int array[], int len) {
        mergeSortSub(array, 0, len - 1);
    }

    private static void mergeSortSub(int[] array, int s, int e) {
        //终止条件
        if (s >= e) {
            return;
        }

        //中间位置
        int mid = (e + s) / 2;

        mergeSortSub(array, s, mid);
        mergeSortSub(array, mid + 1, e);

        //将array[s,mid],array[mid+1,e]合并到array[s,e]
        merge(array, s, mid, e);
    }

    //合并排序好的数组。两个指针，建一个临时数组，比较元素大小 小的往临时数组里放，小的指针往后移，直到一个数组移完位置，
    // 另一个数组的元素直接拷贝到临时数组末尾。
    private static void merge(int[] array, int s, int mid, int e) {
        int[] temp = new int[e - s + 1];
        int i = s;
        int j = mid + 1;

        int index = 0;
        while (i <= mid && j <= e) {
            if (array[i] <= array[j]) {
                temp[index++] = array[i++];
            } else {
                temp[index++] = array[j++];
            }
        }

        //前一个数组移完了,移动后一个数组里的剩余元素
        int leftStart = i;
        int leftEnd = mid;
        //上面i++之后 可能大于mid
        if (i > mid) {
            leftStart = j;
            leftEnd = e;
        }

        for (int x = leftStart; x <= leftEnd; x++) {
            temp[index++] = array[x];
        }

        for (int a = 0; a < temp.length; a++) {
            array[s + a] = temp[a];
        }

    }

}
