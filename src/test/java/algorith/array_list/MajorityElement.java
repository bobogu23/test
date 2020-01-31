package algorith.array_list;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Given an array of size n, find the majority element.
 * The majority element is the element that appears more than ⌊ n/2 ⌋ times.

 You may assume that the array is non-empty and the majority element always exist in the array.

 Example 1:

 Input: [3,2,3]
 2,3,3
 Output: 3
 
 
 Example 2:
 
 Input: [2,2,1,1,1,2,2]
 1,1,1,2,2,2,2
 Output: 2
 
 * @author:ben.gu
 * @Date:2020/1/31 3:33 PM
 */
public class MajorityElement {

    // sort log^n
    public int majorityElement(int[] nums) {
        Arrays.sort(nums);
        int mid = nums.length / 2;

        return nums[mid];
    }

    //Boyer-Moore Majority Vote Algorithm
    // 投票法。如果某个数出现的次数超过半数,那么投反对票后 最终剩余票数大于0.即使有半数反对,最终得票数是1
    //O(n)
    public int majorityElement2(int[] nums) {
        int major = nums[0];
        int count = 1;
        for (int i = 1; i < nums.length; i++) {
            if (major == nums[i]) {
                count++;
            } else if (count == 0) {
                major = nums[i];
                count = 1;
            } else {
                count--;
            }
        }

        return major;
    }
}
