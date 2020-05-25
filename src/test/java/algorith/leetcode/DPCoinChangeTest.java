package algorith.leetcode;

/**
 * https://leetcode.com/problems/coin-change/
 * 给定几个货币，和价格。用最少的货币凑到指定的价格 ,凑不到返回-1
 *
 * 动态规划
 * @author:ben.gu
 * @Date:2020/5/12 10:31 下午
 */
public class DPCoinChangeTest {

    public static void main(String[] args) {
        int[] a = {2};
        int i = coinChange(a, 5);
        System.err.println("step:" + i);
    }

    public static int coinChange(int[] coins, int amount) {
        //coins : 2,5,7,
        //amount : 27

        //最后一步,货币 ak 可能是2,5,7,那么倒数第二步是   f(27-ak)，f(27-ak)有三种情况
        //        f(27-2),f(27-5),f(27-7) ,求这三种最小的值
        int[] f = new int[amount + 1];
        int n = coins.length;
        f[0] = 0;
        //设置1到amout这么多钱的步数
        for (int i = 1; i <= amount; i++) {
            f[i] = Integer.MAX_VALUE;
        }
        for (int i = 1; i <= amount; i++) {
            for (int j = 0; j < n; j++) {
                if (i >= coins[j] && f[i - coins[j]] != Integer.MAX_VALUE) {
                    f[i] = Math.min(f[i - coins[j]] + 1, f[i]);
                }
            }
        }

        if (f[amount] != Integer.MAX_VALUE) {
            return f[amount];
        }
        return -1;

    }

}
