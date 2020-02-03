package algorith.sort;

import java.util.Arrays;

/**
 * 归并排序
 * 将待排序的数组 分成两部分，这两部分各自排序,排序好后 合并这两部分。
 * 被分隔的两部分 各自再继续分隔，直到分隔成两个数字为止。
 * @author:ben.gu
 * @Date:2020/2/3 1:58 PM
 */
public class MergeSort {

    public static void main(String args[]) {
        MergeSort s = new MergeSort();
        int[] array = new int[]{1,26,5,3,25,4,2};
        s.mergeSort(array,7);
        System.err.println("array:"+ Arrays.toString(array));

        array = new int[]{1,26,5,3,25,4,2,-1,100,67};
        s.mergeSort(array,10);
        System.err.println("array:"+ Arrays.toString(array));
    }

    //array:数组，n 数组长度
    public int[] mergeSort(int[] array, int n) {
        mergeSort2(array, 0, n - 1);
        return array;
    }

    /**
     *
     * @param array
     * @param begin 待排序元素初始索引
     * @param end 待排序元素结束索引
     * @return
     */
    private void mergeSort2(int[] array, int begin, int end) {
        if (begin >= end) {
            return;
        }
        int mid = begin + (end - begin) / 2;
        mergeSort2(array, begin, mid);
        mergeSort2(array, mid + 1, end);
        merge(array, begin, mid, end);
    }

    private void merge(int[] array, int begin, int mid, int end) {
        int[] tmp = new int[end - begin + 1];
        int index = 0;
        int s1 = begin;
        int s2 = mid + 1;

        while (s1 <= mid && s2 <= end) {
            if (array[s1] < array[s2]) {
                tmp[index] = array[s1];
                s1++;
            } else {
                tmp[index] = array[s2];
                s2++;
            }
            index++;
        }
        int leftBegin = s1;
        int leftEnd = mid;
        if (s1 > mid) {
            leftBegin = s2;
            leftEnd = end;
        }
        while (leftBegin <= leftEnd) {
            tmp[index] = array[leftBegin];
            index++;
            leftBegin++;
        }

        int i = 0;
        while(i<tmp.length){
            array[begin+i]= tmp[i];
            i++;
        }
    }
}
