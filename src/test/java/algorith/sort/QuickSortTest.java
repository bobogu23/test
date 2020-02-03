package algorith.sort;

import redis.clients.jedis.PipelineBase;

import java.util.Arrays;

/**
 * 快速排序
 * 从数组中随机选一个位置作为中心点，pivot. 将数组分成0-pivot-1, pivot, pivot+1-end ,pivot左边都是小于pivot位置上元素的数
 * pivot右边都是大于pivot位置上元素的数.继续分隔 0-pivot-1, pivot+1 - end,也随机选个中心点,将数组中小于pivot的数放
 * pivot左边，大于pivot的数放pivot右边，一直到无法分隔位置
 *
 * @author:ben.gu
 * @Date:2020/2/3 5:08 PM
 */
public class QuickSortTest {

    public static void main(String args[]) {
        int[] array = {1,30,2,3,40,23};
        QuickSortTest t =  new QuickSortTest();
        t.quickSort(array,6);
        System.err.println("array:"+ Arrays.toString(array));
    }

    // array 待排序数组。n 数组长度
    public void quickSort(int[] array, int n) {
        quickSort1(array, 0, n - 1);
    }

    private void quickSort1(int[] array, int begin, int end) {
        if (begin >= end) {
            return;
        }

        int pivot = partition(array, begin, end);

        quickSort1(array, begin, pivot - 1);
        quickSort1(array, pivot, end);
    }

    private int partition(int[] array, int begin, int end) {

        int pivot = end;
        int p = array[pivot];
        int i = begin;
        //from index 0 to begin-1,the nums are all smaller than the num of p
        //traverse from begin to pivot

        for (int j = begin; j <= end - 1; j++) {
            if (array[j] < p) {
                //swap
                int tmp = array[j];
                array[j] = array[i];
                array[i] = tmp;
                i++;
            }
            j++;
        }

        //swap i,end
        int tmp = array[i];
        array[i] = array[pivot];
        array[pivot] = tmp;

        return i;
    }
}
