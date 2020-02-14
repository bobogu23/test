package algorith.dynamic_plan;

/**
 * https://leetcode.com/problems/minimum-path-sum/
 * Given a m x n grid filled with non-negative numbers,
 * find a path from top left to bottom right
 * which minimizes the sum of all numbers along its path.
 *
 * Input:
 [
 [1,3,1],
 [1,5,1],
 [4,2,1]
 ]
 Output: 7
 Explanation: Because the path 1→3→1→1→1 minimizes the sum.
 * @author:ben.gu
 * @Date:2020/2/13 9:08 AM
 */
public class MinimunPathSum {

    public static void main(String args[]) {
        Solution s = new Solution();
        int[][] grid = { { 1, 3, 1 }, { 1, 5, 1 }, { 4, 2, 1 } };
        System.err.println("min:" + s.minPathSum(grid));
    }

    private static class Solution {
        private int[][] minSum;

        public int minPathSum(int[][] grid) {
            minSum = new int[grid.length][grid[0].length];
            for (int i = 0; i < grid.length; i++) {
                for (int j = 0; j < grid[i].length; j++) {
                    minSum[i][j] = Integer.MAX_VALUE;
                }
            }
            return minSum(0, 0, grid);
        }

        public int minSum(int i, int j, int[][] grid) {
            if (minSum[i][j] != Integer.MAX_VALUE) {
                return minSum[i][j];
            }
            if (i == grid.length - 1 && j == grid[i].length - 1) {
                minSum[i][j] = grid[i][j];
                return minSum[i][j];
            }
            //f(i,j)=min(f(i+1,j),f(i+1,j+1))+grid[i][j];
            int a = Integer.MAX_VALUE;
            //判断是否能往下走
            if (i + 1 < grid.length && j < grid[i].length) {
                a = minSum(i + 1, j, grid);
            }

            int b = Integer.MAX_VALUE;
            //判断是否能往右走
            if (i < grid.length && j + 1 < grid[i].length) {
                b = minSum(i, j + 1, grid);
            }
            minSum[i][j] = Math.min(a, b) + grid[i][j];
            return minSum[i][j];
        }
    }

}
