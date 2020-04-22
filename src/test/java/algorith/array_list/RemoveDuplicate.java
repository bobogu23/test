package algorith.array_list;

/**
 * https://leetcode.com/problems/remove-duplicates-from-sorted-array/
 * 移除数组中的重复元素
 *
 * @author:ben.gu
 * @Date:2020/4/8 11:26 PM
 */
public class RemoveDuplicate {

    //[0,0,1,1,1,2,2,3,3,4],

    public static void main(String args[]) {
        RemoveDuplicate d = new RemoveDuplicate();
        int[] a = {0,0,1,1,1,2,2,3,3,4};
        System.err.println("num:"+d.removeDuplicates(a));
    }

    public int removeDuplicates(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int i = 0;
        for (int j = 1; j < nums.length; j++) {
            if (nums[i] != nums[j]) {
                i++;
                int temp = nums[i];
                nums[i] = nums[j];
                nums[j] = temp;
            }
        }

        return i + 1;
    }
}
