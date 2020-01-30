package algorith.array_list;

import java.io.BufferedReader;

/**
 * 单向链表,支持增删操作
 * reverse
 * find mid node
 * @author:ben.gu
 * @Date:2020/1/28 8:57 PM
 */
public class SingleDirectionList {

    public static void main(String args[]) {
        SingleDirectionList list = new SingleDirectionList();
        list.add(1);
        list.print();

        list.remove(2);
        list.print();

        list.remove(1);
        list.print();

        list.remove(1);
        list.print();

        list.add(2);
        list.add(22);
        list.add(233);
        list.add(244);
        list.add(21);

        list.print();

        Node newHeader = list.reverse(list.header);
        newHeader.print();

        SingleDirectionList list1 = new SingleDirectionList();
        list1.add(1);
        list1.findMidNode().printValue();
        list1.add(2);
        list1.findMidNode().printValue();

        list1.add(3);
        list1.findMidNode().printValue();
        list1.add(4);
        list1.findMidNode().printValue();
        list1.add(4);
        list1.findMidNode().printValue();
    }

    private Node header;

    public void print() {
        StringBuilder sb = new StringBuilder();
        Node tmp = this.header;
        while (tmp != null) {
            sb.append(tmp.val).append(",");
            tmp = tmp.next;
        }
        System.err.println(sb.length() > 0 ? sb.deleteCharAt(sb.length() - 1).toString() : "");
    }

    static class Node {
        private int val;

        private Node next;

        public Node(int val, Node next) {
            this.val = val;
            this.next = next;
        }

        public void print() {
            StringBuilder sb = new StringBuilder("node:").append(val).append(",");
            Node tmp = this.next;
            while (tmp != null) {
                sb.append(tmp.val).append(",");
                tmp = tmp.next;
            }
            System.err.println(sb.deleteCharAt(sb.length() - 1).toString());
        }

        public void printValue() {

            System.err.println(this.val);
        }
    }

    public void add(int value) {
        Node tmp = this.header;
        while (tmp != null && tmp.next != null) {
            tmp = tmp.next;
        }
        if (tmp == null) {
            this.header = new Node(value, null);
        } else {
            tmp.next = new Node(value, null);
        }
    }

    public void remove(int value) {
        Node tmp = this.header;
        Node pre = null;
        while (tmp != null && tmp.val != value) {
            pre = tmp;
            tmp = tmp.next;
        }
        //没找到
        if (tmp == null) {
            return;
        }
        //头节点删除
        if (pre == null) {
            this.header = tmp.next;
        } else {
            pre.next = tmp.next;
        }
        tmp.next = null;
    }

    /**
     * reverse
     * use stack
     * or recursive
     * @return
     */
    public Node reverse(Node header) {
        if (header == null || header.next == null) {
            return header;
        }
        Node tmp = header.next;
        Node newHeader = reverse(header.next);
        tmp.next = header;
        header.next = null;
        return newHeader;
    }

    //quick ,slow point

    // 0,1,2,3,4,5
    //quick 0,2,4
    //slow 0,1,2

    // 0,1,2,3,4,5,6
    //quick 0,2,4,6
    //slow 0,1,2,3

    // 0,1,2
    //quick 0,2
    //slow 0,1

    public Node findMidNode() {
        if (this.header == null || this.header.next == null) {
            return this.header;
        }
        Node slow = this.header;
        Node quick = this.header;

        while (quick.next != null && quick.next.next != null) {
            quick = quick.next.next;
            slow = slow.next;
        }
        return slow;
    }

}
