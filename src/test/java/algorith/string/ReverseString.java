package algorith.string;

import java.util.Arrays;

/**
 * reverse string
 * @author:ben.gu
 * @Date:2020/2/4 2:24 PM
 */
public class ReverseString {

    public static void main(String args[]) {

        char[] c = new char[] { 'H', 'a', 'n', 'n', 'a', 'h' };

        ReverseString s = new ReverseString();
        s.reverseString(c);

        System.err.println("array:" + Arrays.toString(c));
    }

    /**
     * Do not allocate extra space for another array, you must do this by
     * modifying the input array in-place with O(1) extra memory.
     You may assume all the characters consist of printable ascii characters.
    
     example:
     Input: ["H","a","n","n","a","h"]
     Output: ["h","a","n","n","a","H"]
     * @param s
     */

    public void reverseString(char[] s) {
        int left = 0;
        int right = s.length - 1;
        while (left < right) {
            char tmp = s[left];
            s[left] = s[right];
            s[right] = tmp;
            left++;
            right--;
        }
    }
}
