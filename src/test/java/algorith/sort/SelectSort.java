package algorith.sort;

import java.util.Arrays;

/**
 * 选择排序。
 * 数组分成2部分.一部分已经排好序，一部分未排序。遍历未排序部分，每次从未排序中取出最小的数字 放入已排序末尾
 *
 * 时间复杂度，最好情况 O(n),最坏O(n^2)
 * 空间复杂度O(1)
 *
 * 不稳定排序，原地排序
 * @author:ben.gu
 * @Date:2020/2/3 8:02 PM
 */
public class SelectSort {

    public static void main(String args[]) {
        int[] array = new int[] { 1, 2, 3 ,10,11,9,6,100};
        SelectSort s = new SelectSort();
        s.sort(array);
        System.err.println("array:" + Arrays.toString(array));

        array = new int[] { 100, 20, 13 ,11,10,9,6,0};
        s.sort(array);
        System.err.println("array:" + Arrays.toString(array));

        array = new int[] { 100, 20, 13 ,120,11,10,9,6,0};
        s.sort(array);
        System.err.println("array:" + Arrays.toString(array));
    }

    public void sort(int[] array) {

        for (int i = 0; i < array.length; i++) {

            int minIndex = findMin(array, i, array.length - 1);
            int min = array[minIndex];
            //move, i - minIndex-1
            for (int k = minIndex - 1; k >= i; k--) {
                array[k + 1] = array[k];
            }
            array[i] = min;
        }

    }

    private int findMin(int[] array, int i, int i1) {
        int minIndex = i;
        for (int j = i + 1; j <= i1; j++) {
            if (array[minIndex] > array[j]) {
                minIndex = j;
            }
        }
        return minIndex;
    }
}
