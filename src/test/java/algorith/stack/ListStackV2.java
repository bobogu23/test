package algorith.stack;

/**
 * 链式栈 .头节点作为栈顶.时间复杂度 O（1）
 * @author:ben.gu
 * @Date:2020/2/1 1:08 PM
 */
public class ListStackV2 {

    public static void main(String args[]) {
        ListStackV2 stack = new ListStackV2();
        stack.push("a");
        stack.push("a1");
        stack.push("a2");
        stack.push("a3");
        stack.push("a4");

        stack.print();
        System.err.println(stack.pop());
        System.err.println(stack.pop());
        System.err.println(stack.pop());
        System.err.println(stack.pop());
        System.err.println(stack.pop());
        System.err.println(stack.pop());
        System.err.println(stack.pop());

    }

    private Node header;

    public void print() {
        StringBuilder sb = new StringBuilder();
        Node n = this.header;
        while (n != null) {
            sb.append(n.data).append(",");
            n = n.next;
        }
        System.err.println(sb.deleteCharAt(sb.length() - 1).toString());
    }

    static class Node {
        private String data;

        private Node next;

        public Node(String data, Node next) {
            this.data = data;
            this.next = next;
        }

    }

    public boolean push(String data) {
        Node n = new Node(data, null);
        if (this.header == null) {
            this.header = n;
            return true;
        }
        n.next = this.header;
        this.header = n;
        return true;
    }

    public String pop() {
        if (this.header == null) {
            return null;
        }
        Node n = this.header;
        Node next = this.header.next;
        this.header = next;
        return n.data;
    }

}
