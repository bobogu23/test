package algorith.backtrack;

/**
 * 0-1背包问题。
 * 背包能够承受的总重量W，可以放n件物品，每个物品的重量不同,物品不可分割。在不超过背包所能承受的重量的前提下,如何使背包的总重量
 * 最大。
 *
 * @author:ben.gu
 * @Date:2020/2/9 3:08 PM
 */
public class ZeroOneBag {
    private static int maxWeight = Integer.MIN_VALUE;

    public static void main(String args[]) {
        int[] items = new int[] { 20, 10, 50, 30, 40, 70, 80 };
        int n = 7;
        int w = 204;
        ZeroOneBag bag = new ZeroOneBag();
//        bag.f(0, 0, items, n, w);
        bag.f2(0,0,items,n,w);
        System.err.println("maxWeight:" + maxWeight);

    }

    /**
     *
     * @param i 表示在使用哪个物品
     * @param cw 当前已经放入背包的物品总重量
     * @param items 物品列表
     * @param n 物品总数
     * @param w 背包能承受的最大重量
     */

    boolean[] used = new boolean[7];

    public void f(int i, int cw, int[] items, int n, int w) {
        //达到背包能承受的重量，或者达到物品总数
        if (cw == w || n == i) {
            if (cw > maxWeight) {
                maxWeight = cw;
            }
            return;
        }



                f(i + 1, cw, items, n, w);
                if (cw + items[i] <= w) {
                    f(i + 1, cw + items[i], items, n, w);
                }
    }

    /**
     *
     * @param i  遍历到第几个物品
     * @param cw 当前背包中物品的总重量
     * @param items
     * @param n
     * @param w
     */
    public void f2(int i, int cw, int[] items, int n, int w) {
        //达到背包能承受的重量，或者达到物品总数
        if (cw == w || n == i) {
            if (cw > maxWeight) {
                maxWeight = cw;
            }
            return;
        }

        //放物品还是不放物品
        for (int t = 0; t <= 1; t++) {
            if (t == 0) {//不放,进入下个物品的判断
                f2(i + 1, cw, items, n, w);
            } else {
                //放物品，判断是否能装下
                if (cw + items[i] <= w) {
                    f2(i + 1, cw + items[i], items, n, w);
                }
            }
        }

    }

}
