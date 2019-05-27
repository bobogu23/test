package algorith.linkedlist;

/**
 * 单向列表
 * @author:ben.gu
 * @Date:2019/4/22 12:58 PM
 */
public class SinglyLinkedList {

    private Node head;

    static class Node {
        private int data;

        private Node next;

        public Node(int data, Node next) {
            this.next = next;
            this.data = data;
        }
    }

    static String printNode(Node node) {
        if (node == null) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(node.data).append(",");
        Node next = node;
        while ((next = next.next) != null) {
            stringBuilder.append(next.data).append(",");
        }
        return stringBuilder.substring(0, stringBuilder.length() - 1);
    }

    public void addLast(int value) {
        Node newNode = new Node(value, null);
        if (head == null) {
            head = newNode;
        } else {
            Node cur = head;
            while (cur.next != null) {
                cur = cur.next;
            }
            cur.next = newNode;
        }
    }

    public void addLast(Node node) {
        if (head == null) {
            head = node;
        } else {
            Node cur = head;
            while (cur.next != null) {
                cur = cur.next;
            }
            cur.next = node;
        }
    }

    public boolean palindrome() {
        //为空
        if (head == null) {
            return false;
        }
        //一个节点
        if (head.next == null) {
            return true;
        }

        //快慢指针找到中点
        Node slow = head;
        Node fast = head;
        while (slow.next != null && fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        Node leftLink = null;
        Node rightLink = null;
        //判断链表元素个数是奇数还是偶数
        if (fast.next == null) {
            System.err.println("节点为奇数个");
            rightLink = slow.next;
            leftLink = reverse(slow).next;

        } else {
            System.err.println("节点为偶数个");
            rightLink = slow.next;
            leftLink = reverse(slow);
        }

        //对比每个元素是否相等
        return compareNode(rightLink, leftLink);

    }

    private boolean compareNode(Node rightLink, Node leftLink) {
        Node r = rightLink;
        Node l = leftLink;
        while (r != null && l != null) {
            if (l.data == r.data) {
                l = l.next;
                r = r.next;
            } else {
                break;
            }
        }

        return l == null && r == null;
    }

    private Node reverse(Node slow) {
        if (slow != null) {
            //从头节点开始遍历一直到slow，反转
            Node right = this.head;
            //新的头节点
            Node pre = null;
            //下个节点
            Node next;
            while (!right.equals(slow)) {
                next = right.next;
                right.next = pre;
                pre = right;
                right = next;
            }
            //此时right 指向中间节点
            //将right的下个节点 指向之前中间节点的上一个节点
            right.next = pre;
            return right;
        }
        return null;
    }

    public static void main(String args[]) {
        //        SinglyLinkedList linkedList = new SinglyLinkedList();
        //        linkedList.addLast(1);
        //        linkedList.addLast(2);
        //        linkedList.addLast(3);
        //        linkedList.addLast(2);
        //        linkedList.addLast(1);
        //        System.err.println("是否为回文:" + linkedList.palindrome());
        //
        //        SinglyLinkedList linkedList1 = new SinglyLinkedList();
        //        linkedList1.addLast(1);
        //        linkedList1.addLast(1);
        //        linkedList1.addLast(4);
        //        linkedList1.addLast(2);
        //        linkedList1.addLast(1);
        //        System.err.println("linkedList1 是否为回文:" + linkedList1.palindrome());
        //
        //        SinglyLinkedList linkedList2 = new SinglyLinkedList();
        //        linkedList2.addLast(1);
        //        linkedList2.addLast(4);
        //        linkedList2.addLast(4);
        //        linkedList2.addLast(1);
        //        System.err.println("linkedList2 是否为回文:" + linkedList2.palindrome());
        //
        //        SinglyLinkedList linkedList3 = new SinglyLinkedList();
        //        linkedList3.addLast(1);
        //        linkedList3.addLast(4);
        //        linkedList3.addLast(4);
        //        linkedList3.addLast(2);
        //        System.err.println("linkedList3 是否为回文:" + linkedList3.palindrome());

        //        SinglyLinkedList linkedList4 = new SinglyLinkedList();
        //        linkedList4.addLast(1);
        //        linkedList4.addLast(2);
        //        linkedList4.addLast(3);
        //        linkedList4.addLast(4);
        //        System.err.println("linkedList4 反转:" + printNode(linkedList4.reverseNode(linkedList4.head)));
        //
        //        SinglyLinkedList linkedList5 = new SinglyLinkedList();
        //        linkedList5.addLast(1);
        //        linkedList5.addLast(2);
        //        linkedList5.addLast(3);
        //        linkedList5.addLast(4);
        //
        //        System.err.println("linkedList5 反转:" + printNode(linkedList5.reverseNode2(linkedList5.head)));

        //        SinglyLinkedList linkedList6 = new SinglyLinkedList();
        //        linkedList6.addLast(1);
        //        linkedList6.addLast(2);
        //        linkedList6.addLast(3);
        //        linkedList6.addLast(4);
        //
        //        System.err.println("linkedList6 取中间节点:" + linkedList6.getMiddleNode(linkedList6.head).data);
        //
        //        SinglyLinkedList linkedList7 = new SinglyLinkedList();
        //        linkedList7.addLast(1);
        //        linkedList7.addLast(2);
        //        linkedList7.addLast(3);
        //        linkedList7.addLast(4);
        //        linkedList7.addLast(5);
        //
        //        System.err.println("linkedList7 取中间节点:" + linkedList7.getMiddleNode(linkedList7.head).data);

        //        SinglyLinkedList linkedList8 = new SinglyLinkedList();
        //        linkedList8.addLast(1);
        //        linkedList8.addLast(2);
        //        linkedList8.addLast(3);
        //        linkedList8.addLast(4);
        //        linkedList8.addLast(5);
        //        linkedList8.addLast(6);
        //        linkedList8.addLast(7);
        //
        //        Node node = new Node(33, null);
        //        Node node1 = new Node(33, node);
        //        Node node2 = new Node(93, node1);
        //        Node node3 = new Node(93, node2);
        //
        //        node.next = node3;
        //        linkedList8.addLast(node3);
        //
        //        System.err.println("linkedList8 是否存在环:" + linkedList8.existLoop(linkedList8.head));

        //        SinglyLinkedList linkedList9 = new SinglyLinkedList();
        //        linkedList9.addLast(1);
        //        linkedList9.addLast(2);
        //        linkedList9.addLast(3);
        //        linkedList9.addLast(4);
        //        linkedList9.addLast(5);
        //        linkedList9.addLast(6);
        //        linkedList9.addLast(7);
        //
        //        Node node = linkedList9.deleteNode(linkedList9.head, 7);
        //
        //        System.err.println("linkedList9 删除倒数第7个节点:" + printNode(node));
        //
        //        SinglyLinkedList linkedList10 = new SinglyLinkedList();
        //        linkedList10.addLast(1);
        //        linkedList10.addLast(2);
        //        linkedList10.addLast(3);
        //        linkedList10.addLast(4);
        //        linkedList10.addLast(5);
        //        linkedList10.addLast(6);
        //        linkedList10.addLast(7);
        //
        //        Node node1 = linkedList10.deleteNode(linkedList10.head, 8);
        //
        //        System.err.println("linkedList10 删除倒数第8个节点:" + printNode(node1));
        //
        //        SinglyLinkedList linkedList11 = new SinglyLinkedList();
        //        linkedList11.addLast(1);
        //        linkedList11.addLast(2);
        //        linkedList11.addLast(3);
        //        linkedList11.addLast(4);
        //        linkedList11.addLast(5);
        //        linkedList11.addLast(6);
        //        linkedList11.addLast(7);
        //
        //        Node node2 = linkedList11.deleteNode(linkedList11.head, 0);
        //
        //        System.err.println("linkedList11 删除倒数第0个节点:" + printNode(node2));

        SinglyLinkedList linkedList12 = new SinglyLinkedList();
        linkedList12.addLast(1);
        linkedList12.addLast(2);
        linkedList12.addLast(3);
        linkedList12.addLast(10);

        SinglyLinkedList linkedList13 = new SinglyLinkedList();

        linkedList13.addLast(5);
        linkedList13.addLast(6);
        linkedList13.addLast(20);

        System.err.println(
                "merge list :" + printNode(linkedList12.mergeTwoList(linkedList12.head, linkedList13.head)));



    }

    /**
     * 取中间节点
     * @param header
     * @return
     */
    private Node getMiddleNode(Node header) {
        if (header == null || header.next == null) {
            return header;
        }

        //快慢指针
        //奇数个节点1,2,3,4,5
        //慢指针 1,2,3
        //快指针 1,3,5

        //偶数个节点 1,2,3,4,5,6
        //慢指针1,2,3
        //快指针1,3,5
        Node slow = header;
        Node fast = header;

        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }
        return slow;

    }

    /**
     * 链表反转
     * @return
     */
    private Node reverseNode(Node header) {

        if (header == null || header.next == null) {
            return header;
        }

        Node pred = header;
        Node c = header.next;
        header.next = null;

        Node right;
        while (c.next != null) {
            right = c.next;
            c.next = pred;
            pred = c;
            c = right;
        }
        c.next = pred;
        return c;
    }

    /**
     * 链表反转2
     * @return
     */
    private Node reverseNode2(Node header) {

        if (header == null || header.next == null) {
            return header;
        } else {
            Node headerNode = reverseNode2(header.next);
            header.next.next = header;
            header.next = null;
            return headerNode;
        }

    }

    /**
     * 链表中是否存在环 
     * @return
     */
    private boolean existLoop(Node header) {
        if (header == null || header.next == null) {
            return false;
        }
        Node slow = header;
        Node fast = header;

        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
            if (slow == fast) {
                return true;
            }
        }
        return false;

    }

    /**
     * 删除倒数第n个节点
     * 1.用2个指针，第一个先走n步(删除节点要知道被删除节点的上一个节点),然后两个指针一起走
     * @param header
     * @param index
     */
    private Node deleteNode(Node header, int index) {
        if (header == null || index <= 0) {
            return header;
        }
        Node n1 = header;
        Node n2 = header;

        int count = 0;

        while (n1.next != null) {
            n1 = n1.next;
            count++;
            //第一个节点移动了n步，第二个节点要开始动了
            if (count > index) {
                n2 = n2.next;
            }
        }

        //删除头节点
        if (count == index - 1) {
            header = head.next;
        }
        //说明index大于链表长度
        else if (n2 != header) {
            n2.next = n2.next.next;
        }

        return header;
    }

    private Node mergeTwoList(Node n1, Node n2) {
        if (n1 == null) {
            return n2;
        }
        if (n2 == null) {
            return n1;
        }

        //新建头节点,指向两个链表中较小的节点
        Node header = null;
        if (n1.data <= n2.data) {
            header = n1;
            header.next = mergeTwoList(n1.next, n2);
        } else {
            header = n2;
            header.next = mergeTwoList(n1, n2.next);
        }

        return header;

    }
}
