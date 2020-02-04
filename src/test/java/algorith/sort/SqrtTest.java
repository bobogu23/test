package algorith.sort;

/**
 * Implement int sqrt(int x).

 Compute and return the square root of x, where x is guaranteed to be a non-negative integer.

 Since the return type is an integer, the decimal digits are truncated
 and only the integer part of the result is returned.


 Input: 8
 Output: 2
 Explanation: The square root of 8 is 2.82842..., and since
 the decimal part is truncated, 2 is returned.
 * @author:ben.gu
 * @Date:2020/2/4 11:23 AM
 */
public class SqrtTest {

    public static void main(String args[]) {
        SqrtTest t = new SqrtTest();
        //        System.err.println("0 sqr:" + t.mySqrt(0));
        //        System.err.println("1 sqr:" + t.mySqrt(1));
        //        System.err.println("2 sqr:" + t.mySqrt(2));
        //        System.err.println("3 sqr:" + t.mySqrt(3));
        //        System.err.println("4 sqr:" + t.mySqrt(4));
        //        System.err.println("8 sqr:" + t.mySqrt(8));
        //        System.err.println("6 sqr:" + t.mySqrt(6));
        //        System.err.println("100 sqr:" + t.mySqrt(100));
        System.err.println("2147395599 sqr:" + t.mySqrt(2147395599));
    }

    public int mySqrt(int x) {
        if (x == 0 || x == 1) {
            return x;
        }
        int low = 1;
        int high = x;

        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (mid == x / mid) {
                return mid;
            }
            if (mid > x / mid) {
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return low - 1;
    }

}
