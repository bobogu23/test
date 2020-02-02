package algorith.recusive;

import java.util.Arrays;

/**
 * 一组数据集合的全排列
 * @author:ben.gu
 * @Date:2020/2/1 11:11 PM
 */
public class Sort {

    public static void main(String args[]) {
        Sort sort = new Sort();
        int[] array = { 1, 10, 1 };
        int[] sort1 = sort.sort(array);
        System.err.println(Arrays.toString(sort1));

        array = new int[] { 1, 10, 1, 100 };
        sort1 = sort.sort(array);
        System.err.println(Arrays.toString(sort1));

        array = new int[] { 1, 10, 1, 100, 9, 20 };
        sort1 = sort.sort(array);
        System.err.println(Arrays.toString(sort1));

        array = new int[] { 1, 10, 1, 100, 9, 20,4 };
        sort1 = sort.sort(array);
        System.err.println(Arrays.toString(sort1));
    }

    public int[] sort(int[] array) {
        //拆成两对两对的元素比较，然后跟比较好的元素做合并
        if (array.length == 1) {
            return array;
        }
        if (array.length == 2) {
            if (array[0] > array[1]) {
                int t = array[0];
                array[0] = array[1];
                array[1] = t;
            }
            return array;
        }
        int mid = array.length / 2;
        int[] r1 = sort2(array, mid, array.length - 1);
        int[] r2 = sort2(array, 0, mid - 1);

        return merge2(r1, r2);
    }

    private int[] merge(int[] array, int p1Start, int p2Start, int p2End) {
        int[] r = new int[p2End - p1Start + 1];
        int p1End = p2Start - 1;
        int ps1 = p1Start;
        int ps2 = p2Start;

        int index = 0;
        while (ps1 <= p1End && ps2 <= p2End) {
            if (array[ps1] < array[ps2]) {
                r[index] = array[ps1];
                ps1++;
            } else {
                r[index] = array[ps2];
                ps2++;
            }
            index++;
        }

        int begin = ps1;
        int end = p1End;
        if (ps2 <= p2End) {
            begin = ps2;
            end = p2End;
        }
        do {
            r[index] = array[begin];
            begin++;
            index++;
        } while (begin <= end);

        return r;
    }

    private int[] merge2(int[] array1, int[] array2) {
        int[] r = new int[array1.length + array2.length];
        int p1End = array1.length - 1;
        int p2End = array2.length - 1;
        int ps1 = 0;
        int ps2 = 0;

        int index = 0;
        while (ps1 <= p1End && ps2 <= p2End) {
            if (array1[ps1] < array2[ps2]) {
                r[index] = array1[ps1];
                ps1++;
            } else {
                r[index] = array2[ps2];
                ps2++;
            }
            index++;
        }

        int begin = ps1;
        int end = p1End;
        int[] array = array1;
        if (ps2 <= p2End) {
            begin = ps2;
            end = p2End;
            array = array2;
        }
        do {
            r[index] = array[begin];
            begin++;
            index++;
        } while (begin <= end);

        return r;
    }

    public int[] sort2(int[] array, int begin, int end) {
        if (end - begin > 1) {
            int mid = begin + (end - begin) / 2;
            int[] r1 = sort2(array, begin, mid - 1);
            int[] r2 = sort2(array, mid, end);
            return merge2(r1, r2);
        } else if (end - begin == 0) {
            return new int[] { array[begin] };
        }
        return merge(array, begin, end, end);
    }
}
