package algorith.leetcode;

import org.springframework.test.web.servlet.htmlunit.webdriver.WebConnectionHtmlUnitDriver;

/**
 * @author:ben.gu
 * @Date:2020/5/27 10:53 下午
 */
public class SingleListReverseTest {
    public static void main(String[] args) {
        SingeList list = new SingeList();
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        list.add("5");
        list.add("6");
        list.print();
        list.reverse();
        list.print();
        list.reverse1();
        list.print();
    }

    static class SingeList {
        private Node header;

        public void print() {
            Node n = this.header;
            StringBuilder s = new StringBuilder();

            while (n != null) {
                s.append(n.a).append(",");
                n = n.next;
            }
            System.err.println(s.substring(0, s.length() - 1));
        }

        public void add(String s) {
            Node n = this.header;
            if (n == null) {
                this.header = new Node(s);
                return;
            }
            while (n.next != null) {
                n = n.next;
            }
            n.next = new Node(s);
        }

        public void reverse() {
            Node pre = null;
            Node next = null;
            Node cur = this.header;
            while (cur != null) {
                next = cur.next;
                cur.next = pre;
                pre = cur;
                cur = next;
            }
            this.header = pre;
        }

        public void reverse1() {
            Node n = this.header;
            this.header = reverseNode(n);
        }

        private Node reverseNode(Node n) {
            if (n == null || n.next == null) {
                return n;
            }
            Node next = n.next;
            Node temp = reverseNode(n.next);
            next.next = n;
            n.next = null;
            return temp;
        }

    }

    static class Node {
        private String a;

        private Node next;

        public Node(String a) {
            this.a = a;
        }
    }

}
