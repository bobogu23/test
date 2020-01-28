package algorith.array_list;

/**
 * 两个有序数组合并
 * @author:ben.gu
 * @Date:2020/1/28 8:12 PM
 */
public class SortedArrayMergeTest {

    public static void main(String args[]) {
        int[] a = new int[] { 1, 2, 9, 20, 35 };
        int[] b = new int[] { 4, 5, 21, 34, 55 };
        int[] c = merge(a, b);

        print(c);
    }

    public static void print(int[] array) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < array.length; i++) {
            sb.append(array[i]).append(",");
        }
        System.err.println(sb.deleteCharAt(sb.length() - 1).toString());
    }

    public static int[] merge(int[] arrayA, int[] arrayB) {

        int[] temp = new int[arrayA.length + arrayB.length];
        //比较2个数组中元素的大小,将小的元素优先放入临时数组 直到一个数组遍历完。剩下的数组将剩余元素拷贝到临时数组
        int indexA = 0;
        int indexB = 0;

        int index = 0;
        for (; indexA < arrayA.length && indexB < arrayB.length;) {
            int a = arrayA[indexA];
            int b = arrayB[indexB];
            if (a < b) {
                temp[index] = a;
                indexA++;
            } else {
                temp[index] = b;
                indexB++;
            }
            index++;
        }

        if (indexA < arrayA.length - 1) {
            for (; indexA < arrayA.length; indexA++) {
                temp[index] = arrayA[indexA];
                index++;
            }
        } else {
            for (; indexB < arrayB.length; indexB++) {
                temp[index] = arrayB[indexB];
                index++;
            }
        }

        return temp;

    }
}
