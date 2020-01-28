package algorith.array_list;

/**
 * 单向链表,支持增删操作
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

}
