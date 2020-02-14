package algorith.dynamic_plan;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * https://leetcode.com/problems/triangle/
 * 三角形最小路径和
 * @author:ben.gu
 * @Date:2020/2/13 8:03 PM
 */
public class TriangleMin {

    public static void main(String args[]) {
        TriangleMin t = new TriangleMin();

        List<List<Integer>> list = new ArrayList<>();
        //[[-1],[2,3],[1,-1,-3]]
        list.add(Arrays.asList(-1));
        list.add(Arrays.asList(2, 3));
        list.add(Arrays.asList(1, -1, -3));
        //        list.add(Arrays.asList(4, 1, 8, 3));

        int i = t.minimumTotal(list);
        System.err.println("result:" + i);
    }

    /**
     * [
     [2],
     [3,4],
     [6,5,7],
     [4,1,8,3]
     ]
     2 + 3 + 5 + 1 = 11
     从顶部到底部路径上数字之和的最小值,节点到下一行只能去相邻的节点.
     每一行中，从顶部到当前这行每个节点的路径之和最小值 跟上一行上每个节点路径之和最小值有关。
     * @param triangle
     * @return
     */

    public int minimumTotal(List<List<Integer>> triangle) {
        if (triangle == null || triangle.size() == 0) {
            return 0;
        }
        //求到每一行的每个节点的路径的数字最小之和.节点只能往下走或者往右下走
        //创建临时数组,保存从顶点开始到该行每个节点的最小路径和.
        //创建数组保存上一行中到每个节点的路径数字之和最小值.
        int[] temp = new int[triangle.size()];
        temp[0] = triangle.get(0).get(0);
        int[] pre = new int[triangle.size()];
        pre[0] = temp[0];

        for (int i = 1; i < triangle.size(); i++) {
            int l = triangle.get(i).size();
            for (int j = 0; j < l; j++) {
                temp[j] = triangle.get(i).get(j)
                        + Math.min(j == l - 1 ? pre[j - 1] : pre[j], j == 0 ? pre[j] : pre[j - 1]);
            }
            pre = temp.clone();
        }

        //求数组中的最小值
        int min = temp[0];
        for (int k = 1; k < temp.length; k++) {
            if (temp[k] < min) {
                min = temp[k];
            }
        }
        return min;
    }

}
