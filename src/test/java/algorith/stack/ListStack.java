package algorith.stack;

/**
 * 链式栈.时间复杂度O(n)
 * @author:ben.gu
 * @Date:2020/2/1 1:08 PM
 */
public class ListStack {

    public static void main(String args[]) {
        ListStack stack = new ListStack();
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

    public ListStack() {
        this.header = new Node(null, null);
    }

    public void print() {
        StringBuilder sb = new StringBuilder();
        Node n = this.header.next;
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
        Node h = this.header;
        while (h.next != null) {
            h = h.next;
        }
        h.next = n;
        return true;
    }

    public String pop() {
        Node n = this.header;
        Node pre = this.header;
        while (n.next != null) {
            pre = n;
            n = n.next;
        }
        pre.next = null;
        return n.data;
    }

}
