package algorith.array_list;

/**
 * 双向链表.支持增删操作
 * @author:ben.gu
 * @Date:2020/1/30 2:21 PM
 */
public class DLinkedList {
    public static void main(String args[]) {
        DLinkedList list = new DLinkedList();
        list.add(0, 10);
        list.add(1);
        list.add(1, 90);
        list.print();
        list.add(0, 40);
        list.add(1, 3);
        list.add(3);
        list.add(90);
        list.print();

        list.remove(90);
        list.print();
        list.removeAt(0);
        list.print();
        list.removeAt(3);
        list.print();

        list.removeAt(1);
        list.print();

    }

    private Node header;

    private int length;

    static class Node {
        private int data;

        private Node next;

        private Node pre;

        public Node(int data, Node next, Node pre) {
            this.data = data;
            this.next = next;
            this.pre = pre;
        }
    }

    public void remove(int data) {
        Node tmp = this.header;
        while (tmp != null) {
            if (tmp.data == data) {
                //modify succeed node pre
                Node pre = tmp.pre;
                if (tmp.next != null) {
                    tmp.next.pre = pre;
                }
                //modify previous node next node
                if (pre != null) {
                    pre.next = tmp.next;
                }
                length--;
            }
            tmp = tmp.next;
        }

    }

    public void removeAt(int index) {
        if (index >= length) {
            throw new IllegalArgumentException("index out of bound");
        }

        Node tmp = this.header;

        for (int i = 0; i < index; i++) {
            tmp = tmp.next;
        }
        Node pre = tmp.pre;
        if (tmp.next != null) {
            tmp.next.pre = pre;
        }
        if (pre != null) {
            pre.next = tmp.next;
        }
        if (index == 0) {
            this.header = tmp.next;
        }
        length--;
    }

    public void add(int index, int value) {
        if (this.header == null) {
            this.header = new Node(value, null, null);
            length++;
            return;
        }
        if (index >= length) {
            throw new IllegalArgumentException("index out of bound");
        }
        if (index == 0) {
            Node n = new Node(value, this.header, null);
            this.header.pre = n;
            this.header = n;
            length++;
            return;
        }

        Node tmp = this.header;
        for (int i = 0; i < index; i++) {
            tmp = tmp.next;
        }
        Node n = new Node(value, tmp, tmp.pre);
        if (tmp.pre != null) {
            tmp.pre.next = n;
        }
        tmp.pre = n;
        length++;
    }

    public void add(int data) {
        if (this.header == null) {
            this.header = new Node(data, null, null);
            length++;
            return;
        }
        //find tail node
        Node tailNode = findTailNode();
        Node tail = new Node(data, null, tailNode);
        tailNode.next = tail;
        length++;
    }

    public void print() {
        StringBuilder sb = new StringBuilder("list:");
        Node tmp = this.header;
        while (tmp != null) {
            sb.append(tmp.data).append(",");
            tmp = tmp.next;
        }
        System.err.println(sb.deleteCharAt(sb.length() - 1).toString());
    }

    private Node findTailNode() {
        Node tmp = this.header;
        while (tmp.next != null) {
            tmp = tmp.next;
        }
        return tmp;
    }
}
