package algorith.array_list;

import com.alibaba.fastjson.JSON;

/**
 *
 * https://leetcode-cn.com/problems/move-zeroes/
 * 移动数组中的0
 * @author:ben.gu
 * @Date:2020/4/8 10:42 PM
 */
public class MoveZero {
    public static void main(String args[]) {
        MoveZero  mz = new MoveZero();
        int[] nums = {3,4,5,6,0,0,1};

        mz.moveZeroes(nums);
        System.err.println("nums:"+ JSON.toJSONString(nums));
    }

    /**
     * Input: [0,1,0,3,12]
      Output: [1,3,12,0,0]
     * @param nums
     */
    public void moveZeroes(int[] nums) {
        if(nums == null || nums.length == 0){
            return;
        }

        for(int i = 0 ,j = 0;i < nums.length; i++){
           if(nums[i] != 0){
               int temp = nums[i];
               nums[i] = nums[j];
               nums[j] = temp ;
               j++;
           }

        }
    }
}
