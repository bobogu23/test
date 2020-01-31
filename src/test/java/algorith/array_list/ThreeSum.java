package algorith.array_list;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Given an array nums of n integers, are there elements a, b, c in nums such that a + b + c = 0?
 * Find all unique triplets in the array which gives the sum of zero.

 Note:

 The solution set must not contain duplicate triplets.

 Example:

 Given array nums = [-1, 0, 1, 2, -1, -4],

 A solution set is:
 [
 [-1, 0, 1],
 [-1, -1, 2]

 ]
 * @author:ben.gu
 * @Date:2020/1/30 8:49 PM
 */
public class ThreeSum {

    public static void main(String args[]) {
        int[] array = new int[] { -4, -2, 1, -5, -4, -4, 4, -2, 0, 4, 0, -2, 3, 1, -5, 0 };
        Arrays.sort(array);
        System.err.println(JSON.toJSONString(array));
        ThreeSum t = new ThreeSum();
        //[-4,-2,1,-5,-4,-4,4,-2,0,4,0,-2,3,1,-5,0]
        List<List<Integer>> lists = t.threeSum(array);
        System.err.println(lists);

        List<List<Integer>> lists1 = t.threeSum2(array);
        System.err.println(lists1);

        array = new int[] { 0, 0, 0 };
        Arrays.sort(array);
        lists1 = t.threeSum2(array);
        System.err.println(lists1);

    }

    /**
     * 先从小到大排序。
     * 三数之和等于0,肯定有个正数,遍历非正整数。
     * @param nums
     * @return
     */
    public List<List<Integer>> threeSum2(int[] nums) {
        if (nums == null || nums.length < 3) {
            return new ArrayList<>();
        }
        Arrays.sort(nums);
        List<List<Integer>> list = new ArrayList<>();
        //判断 num[i],后面的两个数之和是否有等于 -num[i]的
        for (int i = 0; i < nums.length - 1; i++) {
            if (nums[i] > 0) {
                break;
            }

            //判断是否有重复
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            int left = i + 1;
            int right = nums.length - 1;
            while (left < right) {
                int n1 = nums[left];
                int n2 = nums[right];
                if (n1 + n2 == -nums[i]) {
                    list.add(Arrays.asList(nums[i], nums[left], nums[right]));
                    //判断是否有重复元素
                    while (left < right && nums[left] == nums[left + 1]) {
                        left++;
                    }
                    while (left < right && nums[right] == nums[right - 1]) {
                        right--;
                    }

                    left++;
                    right--;
                } else if (-nums[i] > n1 + n2) {
                    left++;
                } else {
                    right--;
                }
            }
        }

        return list;
    }

    public List<List<Integer>> threeSum(int[] nums) {

        if (nums == null || nums.length < 3) {
            return new ArrayList<>();
        }
        List<List<Integer>> list = new ArrayList();

        for (int i = 0; i < nums.length - 2; i++) {
            for (int j = i + 1; j < nums.length - 1; j++) {
                for (int k = j + 1; k < nums.length; k++) {

                    if (nums[i] + nums[j] + nums[k] == 0) {
                        boolean duplicate = false;
                        int[] sorted = sort(nums[i], nums[j], nums[k]);
                        for (int m = 0; m < list.size(); m++) {
                            List<Integer> ls = list.get(m);
                            if (sorted[0] == ls.get(0) && sorted[1] == ls.get(1) && sorted[2] == ls.get(2)) {
                                duplicate = true;
                                break;
                            }
                        }
                        if (!duplicate) {
                            List<Integer> l = new ArrayList();
                            l.add(sorted[0]);
                            l.add(sorted[1]);
                            l.add(sorted[2]);
                            list.add(l);
                        }

                    }
                }
            }
        }
        return list;
    }

    public int[] sort(int a, int b, int c) {
        int[] r = new int[3];
        if (a > b) {
            a = a ^ b;
            b = a ^ b;
            a = b ^ a;
        }

        if (a > c) {
            a = a ^ c;
            c = a ^ c;
            a = c ^ a;
        }
        if (b > c) {
            b = b ^ c;
            c = b ^ c;
            b = c ^ b;
        }
        r[0] = a;
        r[1] = b;
        r[2] = c;

        return r;
    }
}
