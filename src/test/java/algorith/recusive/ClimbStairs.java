package algorith.recusive;

/**
 * 爬楼梯，
 *
 * You are climbing a stair case. It takes n steps to reach to the top.

 Each time you can either climb 1 or 2 steps. In how many distinct ways can you climb to the top?

 Note: Given n will be a positive integer.


 Input: 3
 Output: 3
 Explanation: There are three ways to climb to the top.
 1. 1 step + 1 step + 1 step
 2. 1 step + 2 steps
 3. 2 steps + 1 step


 Input: 2
 Output: 2
 Explanation: There are two ways to climb to the top.
 1. 1 step + 1 step
 2. 2 step
 * @author:ben.gu
 * @Date:2020/2/3 12:08 PM
 */
public class ClimbStairs {

    //n 是楼梯台阶数量
    // f(1)=1 : 1
    //f(2) =2 : 1+1 ,2
    //f(3) = 3: 1+1+1,1+2,2+1
    //f(4) = 5 : 1+1+1+1,1+1+2,2+2,2+1+1,1+2+1

    public static void main(String args[]) {
        ClimbStairs c = new ClimbStairs();
        long time = System.currentTimeMillis();
        int ways = c.climbStairs(45);
        System.err.println("ways:" + ways+",time cost:"+(System.currentTimeMillis()-time));

        time = System.currentTimeMillis();
        ways = c.climbStairs2(45);
        System.err.println("ways2:" + ways+",time cost:"+(System.currentTimeMillis()-time));
    }

    public int climbStairs(int n) {
        if (n == 1) {
            return 1;
        }
        if (n == 2) {
            return 2;
        }
        int step1 = n - 1;
        int step2 = n - 2;
        int w1 = climbStairs(step1);
        int w2 = climbStairs(step2);
        return w1 + w2;
    }

    //斐波那契数列
    public int climbStairs2(int n) {
        if (n == 1) {
            return 1;
        }
        if (n == 2) {
            return 2;
        }
        int first = 1;
        int second = 2;
        for (int i = 3; i <= n; i++) {
            int t = first + second;
            first = second;
            second = t;
        }
        return second;
    }

}
