package algorith.array_list;

/**
 * sorted list,asc
 * merge
 * 
 * @author:ben.gu
 * @Date:2020/1/30 7:36 PM
 */
public class SortedList {

    public static void main(String args[]) {
        SortedList list1 = new SortedList();
        list1.add(1);
        list1.add(10);
        list1.add(11);
        list1.add(13);
        list1.add(19);

        SortedList list2 = new SortedList();
        list2.add(3);
        list2.add(6);
        list2.add(12);
        list2.add(14);
        list2.add(29);
        list2.add(49);
        list2.add(69);
        list2.add(79);

        Node merge = list1.merge(list2.header);
        merge.print();

    }

    private Node header;

    static class Node {
        private int data;

        private Node next;

        public Node(int data, Node next) {
            this.data = data;
            this.next = next;
        }

        public void print() {
            Node tmp = this;
            StringBuilder sb = new StringBuilder("node:").append(this.data).append(",");
            while (tmp.next != null) {
                sb.append(tmp.next.data).append(",");
                tmp = tmp.next;
            }

            System.err.println(sb.deleteCharAt(sb.length() - 1).toString());
        }
    }

    public void add(int data) {
        if (this.header == null) {
            this.header = new Node(data, null);
            return;
        }
        Node tmp = this.header;
        while (tmp.next != null) {
            tmp = tmp.next;
        }
        tmp.next = new Node(data, null);
    }

    public Node merge(Node header) {
        if (header == null) {
            return this.header;
        }
        if (this.header == null) {
            return header;
        }
        Node h1 = this.header;
        Node h2 = header;
        Node newHeader = h1.data > h2.data ? h2 : h1;
        if (h1.data > h2.data) {
            h2 = h2.next;
        } else {
            h1 = h1.next;
        }
        Node tmp = newHeader;
        while (h1 != null && h2 != null) {
            if (h1.data > h2.data) {
                tmp.next = h2;
                h2 = h2.next;
            } else {
                tmp.next = h1;
                h1 = h1.next;
            }
            tmp = tmp.next;
        }
        //left node append tmp
        if (h1 != null) {
            tmp.next = h1;
        } else {
            tmp.next = h2;
        }

        return newHeader;
    }

}
