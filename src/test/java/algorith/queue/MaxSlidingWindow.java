package algorith.queue;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Iterator;

/**
 * @author:ben.gu
 * @Date:2020/2/2 11:40 PM
 */
public class MaxSlidingWindow {

    public static void main(String args[]) {
        //[1,3,-1,-3,5,3,6,7]
        //        3
        MaxSlidingWindow w = new MaxSlidingWindow();
        int[] nums = w.maxSlidingWindow2(new int[] { 1, 3, -1, -3, 5, 3, 6, 7 }, 3);
        System.err.println(Arrays.toString(nums));

    }

    //滑动窗口，窗口每次滑动一步，取窗口中最大的数的集合.窗口大小k
    public int[] maxSlidingWindow(int[] nums, int k) {
        int[] queue = new int[nums.length - k + 1];
        int index = 0;
        for (int i = 0; i < queue.length; i++) {
            int max = nums[i];
            for (int j = i + 1; j < i + k; j++) {
                if (max < nums[j]) {
                    max = nums[j];
                }
            }
            queue[index] = max;
            index++;
        }

        return queue;
    }

    public int[] maxSlidingWindow2(int[] nums, int k) {
        if (nums == null || nums.length == 0) {
            return new int[] {};
        }
        ArrayDeque deque = new ArrayDeque(k);
        int max = nums[0];
        for (int i = 0; i < k; i++) {
            deque.addLast(nums[i]);
            if (max < nums[i]) {
                max = nums[i];
            }
        }

        int[] queue = new int[nums.length - k + 1];
        queue[0] = max;
        int index = 1;
        for (int i = 1; i + k <= nums.length; i++) {
            deque.pollFirst();
            deque.addLast(nums[i + k - 1]);
            queue[index] = maxNum(deque);
            index++;
        }

        return queue;
    }

    private int maxNum(ArrayDeque deque) {
        Iterator iterator = deque.iterator();
        int max = (int) deque.peek();
        while (iterator.hasNext()) {
            int next = (int) iterator.next();
            max = max < next ? next : max;
        }
        return max;
    }
}
