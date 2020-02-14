package algorith.dynamic_plan;

/**
 * 三角形，求数字之和最大值
 *
 * https://blog.csdn.net/ailaojie/article/details/83014821
 * 
 * @author:ben.gu
 * @Date:2020/2/12 9:39 PM
 */
public class Triangle {
    private static int[][] triangle;

    private static int levels;

    private static int[][] maxSum;

    public static void main(String args[]) {

        long start = System.currentTimeMillis();
        System.err.println("max:" + getMax(100, 1, 1));
        System.err.println("time cost:" + (System.currentTimeMillis() - start));
    }

    public static int getMax(int n, int a, int b) {
        triangle = new int[n + 1][n + 1];
        maxSum = new int[n + 1][n + 1];
        //创建三角形
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= i; j++) {
                triangle[i][j] = j;
                maxSum[i][j] = -1;
            }
        }
        levels = n;

        return max(a, b);
    }

    /**
     * 从第i行，第j列开始 到底边的路径上数字最大值
     * @param i
     * @param j
     * @return
     */
    public static int max(int i, int j) {
        if (maxSum[i][j] != -1) {
            return maxSum[i][j];
        }
        if (i == levels && j <= i) {
            maxSum[i][j] = triangle[i][j];
        } else {
            // f(n,j)= max(f(n+1,j),f(n+1,j+1))+triangle(i,j)
            //往下或者往右走的最大值+当前值
            int x = max(i + 1, j);
            int y = max(i + 1, j + 1);
            maxSum[i][j] = Math.max(x, y) + triangle[i][j];
        }

        return maxSum[i][j];
    }

}
