package algorith.sort;

/**
 * 二分查找
 * @author:ben.gu
 * @Date:2019/6/20 11:00 PM
 */
public class BinarySearchTest {

    //******以数据是从小到大排列为前提******

    //查找第一个给定值的元素
    //二分查找，如果a[mid]==目标value。如果mid==0,说明是第一个元素 肯定是第一个要查找的元素。
    // 如果a[mid-1] !=目标value,说明也是第一个要查找的元素。否则继续往前查找

    //查找最后一个给定值的元素

    //查找第一个大于等于给定值的元素

    //查找最后一个小于等于给定值的元素

    public static  int findFirst(int[] array, int value) {
        int low = 0;
        int high = array.length - 1;
        while (low <= high) {
            int mid = low + ((high - low) >> 1);
            if (array[mid] > value) {
                high = mid - 1;
            } else if (array[mid] < value) {
                low = mid + 1;
            } else {
                if (mid == 0 || array[mid - 1] != value) {
                    return mid;
                } else {
                    high = mid - 1;
                }
            }
        }

        return -1;
    }

    public static void main(String args[]) {
//        //11111111
//        System.out.println(int2ip(167773121));
//        System.out.println(ip2int("10.0.3.193"));

        int[] array= {0,1,1,2,3,4,5};
        System.err.println("offset:"+findFirst(array,1));

    }

    public static String int2ip(long ipInt) {
        StringBuilder sb = new StringBuilder();
        sb.append((ipInt >> 24) & 0xFF).append(".");
        sb.append((ipInt >> 16) & 0xFF).append(".");
        sb.append((ipInt >> 8) & 0xFF).append(".");
        sb.append(ipInt & 0xFF);
        return sb.toString();
    }

    public static long ip2int(String ip) {
        //ip 32位二进制,分成4段，每段8位
        String[] split = ip.split("\\.");

        Long value = Long.valueOf(split[0]) << 24 | Long.valueOf(split[1]) << 16 | Long.valueOf(split[2]) << 8
                | Long.valueOf(split[3]);

        return value;
    }

}
