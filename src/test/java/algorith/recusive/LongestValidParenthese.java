package algorith.recusive;

import java.util.Stack;

/**
 * Given a string containing just the characters '(' and ')', 
 * find the length of the longest valid (well-formed) parentheses substring.

 Example 1:

 Input: "(()"
 Output: 2
 Explanation: The longest valid parentheses substring is "()"
 Example 2:

 Input: ")()())"
 Output: 4
 Explanation: The longest valid parentheses substring is "()()"

 //穷举出字符串中的相同长度的子字符串,判断是否匹配。
  例如
 ((())

 可以列出的字符对有
  长度为2
  (( (( () ))

  长度为4
  ((()
  (())


 * @author:ben.gu
 * @Date:2020/2/2 12:56 PM
 */
public class LongestValidParenthese {

    public int isValid(String s) {
        if (s == null || s.length() == 1) {
            return 0;
        }
        int count = 0;
        for (int i = 0; i < s.length(); i++) {
            for (int j = i + 2; j < s.length(); j += 2) {
                if (isValid2(s.substring(i, j))) {
                    count = Math.max(count, j - i);
                }
            }
        }
        return count;

    }

    private boolean isValid2(String substring) {
        Stack<Character> stack = new Stack<>();

        for (char c : substring.toCharArray()) {
            switch (c) {
            case '(':
                stack.push(')');
                break;
            default:
                if (stack.isEmpty() || stack.pop() != c) {
                    return false;
                }
                break;
            }
        }

        return stack.isEmpty();
    }
}
