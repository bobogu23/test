package algorith.array_list;

/**
 * 单向循环链表.支持增删操作
 * 循环链表 末尾节点的next指针指向头节点
 * @author:ben.gu
 * @Date:2020/1/28 10:23 PM
 */
public class CycleList {

    public static void main(String args[]) {

        CycleList list = new CycleList();
        list.print();

        list.add(1);
        list.print();
        list.removeAt(0);
        list.print();
        list.add(1);
        list.remove(0);
        list.print();
        list.remove(1);
        list.print();

        list.add(1);
        list.add(11);
        list.add(13);
        list.add(21);
        list.add(31);
        list.add(21);
        list.add(31);
        list.print();
        list.remove(21);
        list.print();
        list.removeAt(0);
        list.print();
        list.removeAt(2);
        list.print();

        list.removeAt(2);
        list.print();

        list.add(0, 100);
        list.print();

        list.add(2, 77);
        list.print();
    }

    static class Node {
        private int value;

        private Node next;

        public Node(int value, Node next) {
            this.value = value;
            this.next = next;
        }
    }

    private Node header;

    private int length;

    public CycleList() {
        this.length = 0;
    }

    public void add(int value) {
        if (this.header == null) {
            this.header = new Node(value, null);
            this.header.next = this.header;
        } else {
            //找到尾节点
            Node tmp = this.header;
            while (tmp.next != this.header) {
                tmp = tmp.next;
            }
            tmp.next = new Node(value, tmp.next);
        }
        length++;
    }

    public void add(int index, int value) {
        if (this.header == null && index == 0) {
            this.header = new Node(value, null);
            this.header.next = this.header;
        } else {
            if (index >= length) {
                throw new IllegalArgumentException("index out of bound");
            }

            if (index == 0) {
                Node tail = findTailNode();
                this.header = new Node(value, this.header);
                tail.next = this.header;
                length++;
                return;
            }
            Node tmp = this.header;
            for (int i = 0; i < index - 1; i++) {
                tmp = tmp.next;
            }
            //tmp : index 的前一个节点
            //tmp.next:index 位置的节点
            tmp.next = new Node(value, tmp.next);
            length++;
        }

    }

    public void remove(int value) {
        if (this.length == 0) {
            return;
        }
        Node tmp = this.header;
        Node pre = this.header;
        for (;;) {
            if (tmp.value == value) {
                //如果是删除头节点 特殊处理
                if (tmp == this.header && tmp.next == this.header) {
                    this.header = null;
                    tmp = null;
                } else {
                    Node deletedNode = tmp;
                    pre.next = tmp.next;
                    pre = tmp;
                    tmp = tmp.next;
                    deletedNode.next = null;

                }
                length--;
            } else {
                pre = tmp;
                tmp = tmp.next;
            }
            if (tmp == null || tmp == this.header) {
                break;
            }

        }
    }

    public void removeAt(int index) {
        if (index >= length) {
            throw new IllegalArgumentException("index out of bound");
        }

        if (index == 0 && this.length == 1) {
            this.header = null;
            length--;
            return;
        }
        if (index == 0) {
            Node tailNode = findTailNode();
            Node deletedNode = this.header;
            this.header = this.header.next;
            deletedNode.next = null;
            //尾节点指向新的头节点
            tailNode.next = this.header;
            length--;
            return;
        }

        Node tmp = this.header;
        for (int i = 0; i < index - 1; i++) {
            tmp = tmp.next;
        }

        Node deletedNode = tmp.next;
        //tmp : index 的前一个节点
        //tmp.next:index 位置的节点
        tmp.next = tmp.next.next;
        deletedNode.next = null;
        length--;
    }

    public Node findTailNode() {
        if (this.length == 0) {
            return null;
        }
        Node tmp = this.header;
        while (tmp.next != this.header) {
            tmp = tmp.next;
        }
        return tmp;
    }

    public void print() {
        if (length > 0) {
            if (length == 1) {
                System.err.println("list:" + this.header.value + ",length:" + this.length);
                return;
            }
            Node tmp = this.header;
            StringBuilder sb = new StringBuilder();
            for (;;) {
                sb.append(tmp.value).append(",");
                tmp = tmp.next;
                if (tmp == this.header) {
                    break;
                }
            }
            System.err.println("list:" + sb.deleteCharAt(sb.length() - 1).toString() + ",length:" + this.length);
        } else {
            System.err.println("list:,length:" + this.length);
        }

    }

}
