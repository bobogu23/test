package algorith.stack;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.math.NumberUtils;
import sun.jvm.hotspot.jdi.IntegerTypeImpl;

import java.util.Map;

/**
 * @author:ben.gu
 * @Date:2019/6/27 8:52 PM
 */
public class StackTest {

    public static void main(String args[]) {
        ArrayStack stack = new ArrayStack(5);

        System.err.println("put:" + stack.push("a"));
        System.err.println("put:" + stack.push("a"));
        System.err.println("put:" + stack.push("a"));
        System.err.println("put:" + stack.push("a"));
        System.err.println("put:" + stack.push("a"));
        System.err.println("put:" + stack.push("a"));
        System.err.println("stack:" + JSON.toJSONString(stack.items));
        System.err.println("pop:" + stack.pop());
        System.err.println("pop:" + stack.pop());
        System.err.println("pop:" + stack.pop());
        System.err.println("pop:" + stack.pop());
        System.err.println("pop:" + stack.pop());
        System.err.println("pop:" + stack.pop());
        System.err.println("pop:" + stack.pop());
        System.err.println("stack:" + JSON.toJSONString(stack.items));

        //表达式计算
        //2个栈，一个放操作数，一个放操作符
        //遍历表达式,遇到数字 放入操作数栈,遇到符号放入操作符号栈
        //符号入栈时如果遇到的栈顶的操作符号比当前符号优先级高或者相等则,取出操作数栈的2个数字进行计算
        ArrayStack numStack = new ArrayStack(5);
        ArrayStack symbolStack = new ArrayStack(5);

        Map<String, Integer> symbolPriority = Maps.newHashMap();
        symbolPriority.put("+", 1);
        symbolPriority.put("-", 1);
        symbolPriority.put("*", 2);
        symbolPriority.put("/", 2);

        String expression = "1+1-3+3+8/3*6-2";//16
        char[] chars = expression.toCharArray();
        int result = 0;
        for (int i = 0; i < chars.length; i++) {
            String s = String.valueOf(chars[i]);
            int symInt = -1;
            if (NumberUtils.isNumber(s)) {
                symInt = Integer.parseInt(s);
            }
            //不是最后一个,数字直接入栈
            if (symInt != -1 && i < chars.length - 1) {
                numStack.push(s);
            } else if (symInt != -1) {
                //最后一个数字,取出数字和符号的栈顶元素参与计算
                String symb = symbolStack.pop();
                Integer num = Integer.valueOf(numStack.pop());
                result = cal(symb, symInt, num);

//                symb = symbolStack.pop();
//                if(symb != null){
//                    num = Integer.valueOf(numStack.pop());
//                    result = cal(symb, result, num);
//                }
                break;
            } else {
                Integer priority = symbolPriority.get(s);

                String peek = symbolStack.peek();
                Integer previousPri = symbolPriority.get(peek);
                //符号栈顶的符号优先级高,先计算前2个数字
                while (peek != null  && previousPri.compareTo(priority) >= 0){
                    //栈顶符号出栈
                    symbolStack.pop();
                    Integer num1 = Integer.parseInt(numStack.pop());
                    Integer num2 = Integer.parseInt(numStack.pop());

                    numStack.push(String.valueOf(cal(peek, num1, num2)));

                    peek = symbolStack.peek();
                    previousPri = symbolPriority.get(peek);
                }

                //当前符号入栈
                symbolStack.push(s);
            }
        }

        //计算结果

        System.err.println("result:" + result);

    }

    public static int cal(String symbol, int num1, int num2) {
        switch (symbol) {
        case "+":
            return num1 + num2;
        case "-":
            return num2 - num1;

        case "*":
            return num2 * num1;

        case "/":
            return num2 / num1;
        }
        return 0;
    }

    static class ArrayStack {
        String[] items;

        int count;

        int capacity;

        public ArrayStack(int capacity) {
            this.capacity = capacity;
            this.count = 0;
            this.items = new String[capacity];

        }

        String[] resize(String[] items) {
            int size = items.length << 1;
            String[] tmp = new String[size];
            for (int i = 0; i < items.length; i++) {
                tmp[i] = items[i];
            }
            return tmp;
        }

        //入栈

        public boolean push(String el) {
            if (count == capacity) {
                //扩容
                items = resize(items);
            }
            items[count++] = el;
            return true;
        }

        //出栈

        public String pop() {
            if (count == 0) {
                return null;
            }
            String item = items[count - 1];
            items[count - 1] = null;
            count--;
            return item;
        }

        /**
         * 查看栈顶元素
         * @return
         */
        public String peek() {
            if (count == 0) {
                return null;
            }
            return items[count - 1];
        }

    }
}
