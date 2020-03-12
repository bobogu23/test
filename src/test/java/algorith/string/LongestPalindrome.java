package algorith.string;

/**
 * 最长回文
 *
 *
 * 给定一个字符串 s，找到 s 中最长的回文子串。你可以假设 s 的最大长度为 1000。

 示例 1：

 输入: "babad"
 输出: "bab"
 注意: "aba" 也是一个有效答案。
 示例 2：

 输入: "cbbd"
 输出: "bb"
 来源：力扣（LeetCode）
 链接：https://leetcode-cn.com/problems/longest-palindromic-substring
 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * @author:ben.gu
 * @Date:2020/3/12 8:31 AM
 */
public class LongestPalindrome {

    public static void main(String args[]) {
        LongestPalindrome p = new LongestPalindrome();

//        String s = "abcba";
                String s = "cbbd";
        System.err.println(p.longestPalindrome(s));

    }

    public String longestPalindrome(String s) {
        //动态规划 如果一个字符串是回文，那么左右两边加上相同的字符一定也是回文。
        //起始位置x,结束位置y,判断从x到y 是否是回文，判断x+1,到y-1 是否是回文

        //回文:可以是一个字母:a，也可以是2个字母bb
        if (s == null || "".equals(s)) {
            return s;
        }

        int l = s.length();

        boolean[][] palindromic = new boolean[l][l];
        int begin = 0;
        int end = 0;
        int maxlen = 1;

        //单个字符肯定是回文
        for (int i = 0; i < l; i++) {
            palindromic[i][i] = true;
        }
        //相邻字符串是否是回文
        for (int i = 0; i < l; i++) {
            if (i + 1 < l && s.charAt(i) == s.charAt(i + 1)) {
                palindromic[i][i + 1] = true;
                maxlen = 2;
                begin = i;
                end = i + 1;
            }
        }

        // 判断 x,y 距离大于2 判断首尾是否是回文
        for (int len = 2; len < l; len++) {
            for (int j = 0; j + len < l; j++) {
                int k = j + len;
                if (s.charAt(j) != s.charAt(k)) {
                    palindromic[j][k] = false;
                } else {
                    if (palindromic[j + 1][k - 1]) {
                        palindromic[j][k] = true;
                        if (len + 1 > maxlen) {
                            maxlen = len + 1;
                            begin = j;
                            end = k;
                        }
                    } else {
                        palindromic[j][k] = false;
                    }
                }
            }

        }

        return s.substring(begin, begin + maxlen);

    }

}
