package algorith.sort;

import java.util.Arrays;

/**
 * 插入排序,
 * 将待排序的数组分成两部分。一部分是已经排序好,0-i.另一部分i+1,end 未排序好,从未排序的部分取出一个数据放到已排序好部分的合适的位置。
 *
 * 时间复杂度 最好情况O(n).
 * 最坏情况 O(n^2).每次都要移动数组元素。
 * 空间复杂度 O(1)
 *
 *
 * 稳定排序,原地排序
 * @author:ben.gu
 * @Date:2020/2/3 7:57 PM
 */
public class InsertSort {

    public static void main(String args[]) {
        int[] array = new int[] { 1, 2, 3 ,10,11,9,6,100};
        InsertSort s = new InsertSort();
        s.sort(array);
        System.err.println("array:" + Arrays.toString(array));
    }

    public void sort(int[] array) {

        for (int i = 1; i < array.length; i++) {
            insert(array, array[i], i);
        }

    }

    private void insert(int[] array, int i, int sortedEnd) {
        for (int j = sortedEnd - 1; j >= 0; j--) {

            if (array[j] < i) {
                //                move from j+1 to sortedEnd - 1 .
                move(array, j + 1, sortedEnd);
                array[j + 1] = i;
                break;
            }
        }

    }

    private void move(int[] array, int i, int sortedEnd) {

        for (int j = sortedEnd - 1; j >= i; j--) {
            array[j + 1] = array[j];
        }
    }
}
