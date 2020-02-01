package algorith.queue;

/**
 * 链式队列
 * @author:ben.gu
 * @Date:2020/2/1 10:00 PM
 */
public class ListQueue {

    private Node header;

    private Node tail;

    public static void main(String args[]) {
        ListQueue q = new ListQueue();
        q.enqueue("a");
        q.enqueue("a1");
        q.enqueue("a2");
        q.enqueue("a3");

        q.print();

        q.dequeue();
        q.print();
        q.dequeue();q.dequeue();q.dequeue(); q.print();
        q.dequeue();
    }

    public void print() {
        if (header != null) {
            StringBuilder sb = new StringBuilder(header.data).append(",");
            Node n = header.next;
            while (n != null) {
                sb.append(n.data).append(",");
                n = n.next;
            }
            System.err.println(sb.deleteCharAt(sb.length() - 1).toString());
        }
        else {
            System.err.println("empty");
        }
    }

    static class Node {
        private String data;

        private Node next;

        public Node(String data, Node next) {
            this.data = data;
            this.next = next;
        }
    }

    public void enqueue(String s) {
        if (header == null) {
            tail = header = new Node(s, null);
            return;
        }
        tail.next = new Node(s, null);
        tail = tail.next;
    }

    public String dequeue() {
        if (header == null) {
            return null;
        }
        Node h = header;
        header = h.next;
        return h.data;
    }
}
