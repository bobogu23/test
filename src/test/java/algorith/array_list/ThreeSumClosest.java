package algorith.array_list;

import java.util.Arrays;

/**
 * Given an array nums of n integers and an integer target,
 * find three integers in nums such that the sum is closest to target.
 * Return the sum of the three integers. You may assume that each input would have exactly one solution.

 Example:

 Given array nums = [-1, 2, 1, -4], and target = 1.

 The sum that is closest to the target is 2. (-1 + 2 + 1 = 2).
 * @author:ben.gu
 * @Date:2020/1/31 10:55 AM
 */
public class ThreeSumClosest {

    public static void main(String args[]) {
        int[] array = { 1, 1, -1, -1, 3 };
        //-1,-1,1,1,3 target -1 . result:   -1,-1,1

        //-4 ,-1, 1, 2,  target 1. result:  1,2,

        ThreeSumClosest c = new ThreeSumClosest();
        int i = c.threeSumClosest(array, -1);
        System.err.println("result:" + i);
        int j = c.threeSumClosest2(array, -1);
        System.err.println("result:" + j);

    }

    public int threeSumClosest(int[] nums, int target) {
        //遍历三数组合,求和 保留三数之和最接近 目标数字的结果
        Arrays.sort(nums);
        int tmp = nums[0] + nums[1] + nums[nums.length - 1];
        for (int i = 0; i < nums.length - 1; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }

            int left = i + 1;
            while (left < nums.length - 1) {
                int right = left + 1;
                while (right < nums.length) {
                    int result = nums[i] + nums[left] + nums[right];
                    if (Math.abs(result - target) < Math.abs(tmp - target)) {
                        tmp = result;
                    }
                    right++;
                }
                left++;
            }

        }
        return tmp;
    }

    public int threeSumClosest2(int[] nums, int target) {
        //遍历三数组合,求和 保留三数之和最接近 目标数字的结果
        Arrays.sort(nums);
        int tmp = nums[0] + nums[1] + nums[nums.length - 1];
        if (tmp == target) {
            return tmp;
        }
        for (int i = 0; i < nums.length - 2; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            int left = i + 1;
            int right = nums.length - 1;
            while (left < right) {
                int result = nums[i] + nums[left] + nums[right];
                if (result == target) {
                    return result;
                }
                if (result > target) {
                    right--;
                    while (right > left & nums[right] == nums[right + 1]) {
                        right--;
                    }
                } else {
                    left++;
                    while (left < right & nums[left] == nums[left - 1]) {
                        left++;
                    }
                }
                if (Math.abs(result - target) < Math.abs(tmp - target)) {
                    tmp = result;
                }
            }

        }
        return tmp;
    }
}
