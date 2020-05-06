package algorith.leetcode;

/**
 * https://leetcode-cn.com/problems/best-time-to-buy-and-sell-stock/description/?utm_source=LCUS&utm_medium=ip_redirect_q_uns&utm_campaign=transfer2china
 * @author:ben.gu
 * @Date:2020/5/5 10:21 下午
 */
public class MaxProfit {
    public static void main(String[] args) {
        MaxProfit profit = new MaxProfit();
        int[]  arr = new int[]{7,1,5,3,6,4};
        int i = profit.maxProfit(arr);
        System.err.println("max:"+i);
        i = profit.maxProfit1(arr);
        System.err.println("max:"+i);

    }

    //卖出价格大于买入价格，同时不能在买入前卖出
    //[7,1,5,3,6,4]
    public int maxProfit(int[] prices) {
        if (prices == null || prices.length == 0) {
            return 0;
        }
        int max = 0;
        int length = prices.length;
        for (int i = 0; i < length - 1; i++) {
            for (int j = i + 1; j < length; j++) {
                max = Math.max(prices[j] - prices[i], max);
            }
        }
        return max;

    }
    //求差值最大值
    public int maxProfit1(int[] prices) {
        if (prices == null || prices.length == 0) {
            return 0;
        }
        int max = 0;
        int min = prices[0];
        int length = prices.length;
        for (int i = 1; i < length ; i++) {
           if(prices[i]>prices[i-1]){
               max = Math.max(max,prices[i]-min);
           }else {
               min = Math.min(prices[i],min);
           }
        }
        return max;

    }
}
