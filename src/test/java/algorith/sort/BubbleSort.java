package algorith.sort;

import java.util.Arrays;

/**
 * 冒泡排序。
 * 遍历数组,两两比较，将较大的或者较小的数据 最终放到数组的末尾。
 *
 * 时间复杂度O(n^2).空间复杂度O(1)
 *
 * 原地排序。稳定排序
 *
 * @author:ben.gu
 * @Date:2020/2/3 8:04 PM
 */
public class BubbleSort {

    public static void main(String args[]) {
        BubbleSort s = new BubbleSort();
        int[] array = {1,33,12,34,55,5,100};
        s.bubbleSort(array);
        System.err.println("array:"+ Arrays.toString(array));

        array = new int[]{1};
        s.bubbleSort(array);
        System.err.println("array:"+ Arrays.toString(array));
    }

    public void bubbleSort(int[] array) {
        int j = array.length - 1;
        while (j > 0) {
            for (int i = 0; i < j; i++) {
                if (array[i] > array[i + 1]) {
                    int tmp = array[i];
                    array[i] = array[i + 1];
                    array[i + 1] = tmp;
                }

            }
            j--;
        }

    }

}
