package algorith.string;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 最长不出现重复字符的字串
 * https://leetcode.com/problems/longest-substring-without-repeating-characters/
 *
 * Input: "abcabcbb"
 Output: 3
 Explanation: The answer is "abc", with the length of 3.

 Input: "bbbbb"
 Output: 1
 Explanation: The answer is "b", with the length of 1.

 Input: "pwwkew"
 Output: 3
 Explanation: The answer is "wke", with the length of 3.
 Note that the answer must be a substring, "pwke" is a subsequence and not a substring.
 *
 * @author:ben.gu
 * @Date:2020/2/21 8:56 AM
 */
public class LongestSubString {

    public static void main(String args[]) {

        //"dvdf"
                System.err.println(lengthOfLongestSubstring("dvdf"));
//        System.err.println(lengthOfLongestSubstring2("tmmzuxt"));

    }

    public static int lengthOfLongestSubstring(String s) {
        //用滑动窗口,用set存储已经存放的字符,可以做到O(1)复杂度的查找
        int maxLen = 0;
        Set<Character> set = new HashSet<>();
        for (int i = 0, j = 0; i < s.length() && j < s.length();) {
            if (set.add(s.charAt(j))) {
                j++;
                maxLen = Math.max(maxLen, j - i);
            } else {
                set.remove(s.charAt(i));
                i++;
            }
        }
        return maxLen;

    }

    public static int lengthOfLongestSubstring2(String s) {
        //用滑动窗口,用set存储已经存放的字符,可以做到O(1)复杂度的查找
        //滑动窗口,用MAP存储字符在字符串中的索引,在索引 i-j 中间发现有重复字符,找到重复字符所在索引 j',直接将i跳到j'+1的
        //位置开始重新统计
        int maxLen = 0;
        Map<Character, Integer> charIndexMap = new HashMap<>();
        for (int i = 0, j = 0; i < s.length() && j < s.length();) {
            if (charIndexMap.containsKey(s.charAt(j))) {
                i = Math.max(charIndexMap.get(s.charAt(j)), i);
            }
            charIndexMap.put(s.charAt(j), j+1);
            maxLen = Math.max(maxLen, j - i + 1);
            j++;

        }
        return maxLen;
        //        int n = s.length(), ans = 0;
        //        Map<Character, Integer> map = new HashMap<>(); // current index of character
        //        // try to extend the range [i, j]
        //        for (int j = 0, i = 0; j < n; j++) {
        //            if (map.containsKey(s.charAt(j))) {
        //                i = Math.max(map.get(s.charAt(j)), i);
        //            }
        //            ans = Math.max(ans, j - i + 1);
        //            map.put(s.charAt(j), j + 1);
        //        }
        //        return ans;
    }
}
