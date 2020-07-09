package algorith.recusive;

/**
 * 斐波那契数列，f(n)=f(n-1)+f(n-2)
 * 1,1,2,3,5,8....
 * @author:ben.gu
 * @Date:2020/2/1 10:50 PM
 */
public class Fbnqshulie {

    public static void main(String args[]) {
        int n = 1;
        System.err.println("n=" + n + ",result:" + fibonacci(n));
        n = 2;
        System.err.println("n=" + n + ",result:" + fibonacci(n));
        n = 3;
        System.err.println("n=" + n + ",result:" + fibonacci(n));
        n = 4;
        System.err.println("n=" + n + ",result:" + fibonacci(n));
        n = 5;
        System.err.println("n=" + n + ",result:" + fibonacci(n));
        n = 6;
        System.err.println("n=" + n + ",result:" + fibonacci(n));

        System.err.println("n1=" + n + ",result:" + fibonacci1(n));

        n = 100000;
        System.err.println("n=" + n + ",result:" + fibonacci1(n));

    }

    public static int fibonacci(int n) {
        if (n == 1 || n == 2) {
            return 1;
        }
        //计算n-1,n-2
        int n1 = n - 1;
        int n2 = n - 2;

        int r1 = fibonacci(n1);
        int r2 = fibonacci(n2);
        return r1 + r2;
    }

    public static int fibonacci1(int n) {
        if (n <= 2) {
            return 1;
        }
        int last = 1;
        int lastNext = 1;
        int result = 1;
        for (int i = 3; i <= n; i++) {
            result = last + lastNext;
            lastNext = last;
            last = result;
        }
        return result;
    }

}
