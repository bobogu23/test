package algorith.array_list;

/**
 * Missing Positive
 * Given an unsorted integer array, find the smallest missing positive integer.

 找到缺失的最小正整数

 Example 1:

 Input: [1,2,0]

 Output: 3
 
 Example 2:

 Input: [3,4,-1,1]

 Output: 2
 
 
 
 Example 3:

 Input: [7,8,9,11,12]


 Output: 1
 Note:

 Your algorithm should run in O(n) time and uses constant extra space.

 [2,1]

 3

 数组中如果填满从小到大递增的最小正整数,那么数组中最大的正整数为数组的长度。最小的正整数为1
 遍历给定数组中的正整数,去掉0，负数，大于数组长度的整数
  [1,2,3]

  num[0]=1
  num[1]=2
  num[2]=3

 num[num[0]-1=0] = num[0]
 num[num[1]-1] = num[1]
 num[num[2]-1]= num[2]

 [3,4,-1,1]
 num[num[0]-1=2] != num[0]

 [-1,4,3,1]

 num[num[1]-1=3] != num[1]

 [-1,1,3,4]

 num[num[1]-1=0] != num[1]

 [1,-1,3,4]





 * @author:ben.gu
 * @Date:2020/1/31 4:25 PM
 */
public class MissingPositive {

    public static void main(String args[]) {
        MissingPositive p = new MissingPositive();
        int[] array ={3,4,-1,1};
        System.err.println(p.firstMissingPositive(array));
    }

    public int firstMissingPositive(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] <= 0 || nums[i] > nums.length || nums[nums[i] - 1] == nums[i] || nums[i] == i + 1) {
                continue;
            }

            swap(nums, i, nums[i] - 1);
            i--;

        }

        int i = 0;
        while (i < nums.length) {
            if (nums[i] != i + 1) {
                return i + 1;
            }
            i++;
        }
        return nums.length + 1;

    }

    private void swap(int[] nums, int i, int i1) {
        int tmp = nums[i];
        nums[i] = nums[i1];
        nums[i1] = tmp;
    }
}
