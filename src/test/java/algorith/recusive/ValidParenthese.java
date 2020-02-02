package algorith.recusive;

import java.util.Stack;

/**
 * 有效的括号
 * Given a string containing just the characters '(', ')', '{', '}', '[' and ']', 
 * determine if the input string is valid.

 An input string is valid if:

 Open brackets must be closed by the same type of brackets.
 Open brackets must be closed in the correct order.
 Note that an empty string is also considered valid.

 Example 1:

 Input: "()"
 Output: true
 
 Example 2:

 Input: "()[]{}"
 Output: true
 
 
 Example 3:
 Input: "(]"
 Output: false
 
 Example 4:
 Input: "([)]"
 Output: false
 
 Example 5:
 Input: "{[]}"
 Output: true
 
 方案1：使用栈,每次入栈 时判断跟栈顶符号是否是配对的，如果是 将栈顶符号出栈。下个符号继续与栈顶符号匹配

 方案2：
 使用栈，遍历字符串中的字符，如果发现是(,[,{ ,将对应配对的字符),],} 放入栈中。
 如果不是 (,[,{ 这些字符串，说明遍历到了 ),],} 这些字符，将这些字符与压入栈中的字符比较是否相等。如果不等结束遍历。
 方案1 要把字符串全部遍历一遍。


 * @author:ben.gu
 * @Date:2020/2/2 12:13 PM
 */
public class ValidParenthese {

    public static void main(String args[]) {
        ValidParenthese p = new ValidParenthese();
        String s = "";
        boolean valid = p.isValid(s);
        System.err.println("string:" + s + ",is match:" + valid);

        s = "()";
        valid = p.isValid(s);
        System.err.println("string:" + s + ",is match:" + valid);

        s = "()[]{}";
        valid = p.isValid(s);
        System.err.println("string:" + s + ",is match:" + valid);

        s = "(]";
        valid = p.isValid(s);
        System.err.println("string:" + s + ",is match:" + valid);

        s = "([)]";
        valid = p.isValid(s);
        System.err.println("string:" + s + ",is match:" + valid);

        s = "{[]}";
        valid = p.isValid(s);
        System.err.println("string:" + s + ",is match:" + valid);

    }

    public boolean isValid2(String s) {
        if (s == null || "".equals(s)) {
            return true;
        }

        Stack<Character> stack = new Stack<>();

        for (char c : s.toCharArray()) {
            switch (c) {
            case '(':
                stack.push(')');
                break;
            case '[':
                stack.push(']');
                break;
            case '{':
                stack.push('}');
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

    public boolean isValid(String s) {
        if (s == null || "".equals(s)) {
            return true;
        }

        Stack<Character> stack = new Stack<>();

        int index = 0;
        while (index < s.length()) {
            char c = s.charAt(index);
            if (stack.isEmpty()) {
                stack.push(c);
            } else {
                char top = stack.peek();
                if (isMatch(top, c)) {
                    stack.pop();
                } else {
                    stack.push(c);
                }
            }
            index++;
        }
        return stack.isEmpty();
    }

    public boolean isMatch(char c, char c1) {

        if (c == '(') {
            return c1 == ')';
        }

        if (c == ')') {
            return c1 == '(';
        }
        if (c == '{') {
            return c1 == '}';
        }
        if (c == '}') {
            return c1 == '{';
        }
        if (c == '[') {
            return c1 == ']';
        }
        if (c == ']') {
            return c1 == '[';
        }
        return false;
    }

}
