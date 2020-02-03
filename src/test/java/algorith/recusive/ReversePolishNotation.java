package algorith.recusive;

import java.util.ArrayDeque;
import java.util.Stack;

/**
 * 逆波兰表达式
 * 普通是算术运算表达式 是将 运算符号放到操作数中间,是中缀表达式。例如 1+2 =3
 *
 * 逆波兰表达式把两个数字的表达式放在两数字后面
 * 例如:1,2,+
 *
 * 
 * +, -, *, /
 * 
 * 
 * Input: ["10", "6", "9", "3", "+", "-11", "*", "/", "*", "17", "+", "5", "+"]
 Output: 22
 Explanation:
 ((10 * (6 / ((9 + 3) * -11))) + 17) + 5
 = ((10 * (6 / (12 * -11))) + 17) + 5
 = ((10 * (6 / -132)) + 17) + 5
 = ((10 * 0) + 17) + 5
 = (0 + 17) + 5
 = 17 + 5
 = 22
 * @author:ben.gu
 * @Date:2020/2/2 5:21 PM
 */
public class ReversePolishNotation {

    public static void main(String args[]) {
        String[] array = new String[] { "4", "13", "5", "/", "+" };

        ReversePolishNotation n = new ReversePolishNotation();
        int i = n.evalRPN(array);
        System.err.println(i);
        i = n.evalRPN2(array);
        System.err.println(i);
    }

    public int evalRPN(String[] tokens) {
        Stack<Integer> stack = new Stack<>();
        for (String t : tokens) {
            if ("+".equals(t) || "*".equals(t) || "-".equals(t) || "/".equals(t)) {
                //pop 2 number
                int num1 = stack.pop();
                int num2 = stack.pop();
                switch (t) {
                case "+":
                    stack.push(num1 + num2);
                    break;
                case "*":
                    stack.push(num1 * num2);
                    break;
                case "-":
                    stack.push(num2 - num1);
                    break;
                case "/":
                    stack.push(num2 / num1);
                    break;
                }
            } else {
                stack.push(Integer.parseInt(t));
            }

        }

        return stack.pop();

    }

    public int evalRPN2(String[] tokens) {
        ArrayDeque<Integer> stack = new ArrayDeque<>();
        for (String t : tokens) {
            if ("+".equals(t)) {
                //pop 2 number
                int num1 = stack.pop();
                int num2 = stack.pop();
                stack.push(num1 + num2);

            } else if("*".equals(t)){
                //pop 2 number
                int num1 = stack.pop();
                int num2 = stack.pop();
                stack.push(num1 * num2);
            }
            else if("-".equals(t)){
                //pop 2 number
                int num1 = stack.pop();
                int num2 = stack.pop();
                stack.push(num2 - num1);
            }else if("/".equals(t)){
                //pop 2 number
                int num1 = stack.pop();
                int num2 = stack.pop();
                stack.push(num2 / num1);
            }
            else {
                stack.push(Integer.parseInt(t));
            }

        }

        return stack.pop();

    }
}
