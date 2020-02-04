package algorith.sort;

/**1.二分查找有序数组
 * 2.二分查找有序数组 中如大于等于给定值的第一个元素.
 * 
 * 二分查找 注意点
 * low 从0开始，
 * high 从length-1开始
 * 循环条件 low <= high
 * 
 * 循环中
 * low =mid+1
 * high=mid-1
 * 
 * @author:ben.gu
 * @Date:2020/2/4 10:52 AM
 */
public class BinarySearchTestV2 {
    public static void main(String args[]) {
        BinarySearchTestV2 t = new BinarySearchTestV2();
        int[] array = new int[] { 1, 3, 4, 10, 90, 100 };
        int i = t.binarySearch(array, 100);
        System.err.println("index:" + i);

        i = t.binarySearch(array, 3);
        System.err.println("index:" + i);

        array = new int[] { 100, 100, 200, 300 };
        i = t.binarySearchFirstGreater(array, 100);
        System.err.println("first greater or equal target index:" + i);
    }

    public int binarySearchFirstGreater(int[] array, int target) {
        int low = 0;
        int high = array.length - 1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (array[mid] >= target) {
                if (mid == 0 || array[mid - 1] < target) {
                    return mid;
                }
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return -1;

    }

    public int binarySearch(int[] array, int target) {
        int low = 0;
        int high = array.length - 1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (array[mid] == target) {
                return mid;
            } else if (array[mid] > target) {
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return -1;
    }
}
