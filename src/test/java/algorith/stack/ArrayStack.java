package algorith.stack;

/**
 * 用数组实现一个顺序栈
 * @author:ben.gu
 * @Date:2020/2/1 1:01 PM
 */
public class ArrayStack {

    public static void main(String args[]) {
        ArrayStack stack = new ArrayStack(4);

        System.err.println(stack.push("a"));

        System.err.println(stack.push("a1"));
        System.err.println(stack.push("b1"));
        System.err.println(stack.push("c1"));
        System.err.println(stack.push("d1"));
        System.err.println(stack.pop());
        System.err.println(stack.pop());
    }

    private String[] array;

    private int index;

    private int capacity;

    public ArrayStack(int capacity) {
        this.index = 0;
        this.capacity = capacity;
        this.array = new String[capacity];
    }

    public boolean push(String ele) {
        if (index == capacity) {
            return false;
        }

        array[index] = ele;
        index++;
        return true;
    }

    public String pop() {
        if (index == 0) {
            return null;
        }

        String ele = array[index - 1];
        index--;
        return ele;
    }
}
