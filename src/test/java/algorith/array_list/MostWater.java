package algorith.array_list;

/**
 * https://leetcode-cn.com/problems/container-with-most-water/
 *
 * @author:ben.gu
 * @Date:2020/4/9 11:48 下午
 */
public class MostWater {

    public static void main(String[] args) {
        MostWater water = new MostWater();
        int[] array = { 1, 8, 6, 2, 5, 4, 8, 3, 7 };
        System.err.println("result:" + water.maxArea(array));

    }

    //O(N^2)
    public int maxArea(int[] height) {
        //两个元素间的距离*两个数中的最小值,枚举出所有的情况，取最大的乘积
        int max = 0;
        for (int i = 0; i < height.length; i++) {
            for (int j = i + 1; j < height.length; j++) {
                int result = (j - i) * Math.min(height[j], height[i]);
                if (max < result) {
                    max = result;
                }
            }
        }
        return max;
    }

    public int maxArea1(int[] height) {
        //从最左边和最右边 开始往中间收敛,高度小的往中间移动争取能取到更高的高度
        int left = 0, right = height.length - 1;
        int max = 0;

        while (left < right) {
            max = Math.max(max, Math.min(height[left], height[right]) * (right - left));
            if (height[right] < height[left]) {
                right--;
            } else {
                left++;
            }
        }

        return max;
    }

}
