package algorith.linkedlist;

/**
 * 单向列表
 * @author:ben.gu
 * @Date:2019/4/22 12:58 PM
 */
public class SinglyLinkedList {

    private Node head;

    class Node {
        private int data;

        private Node next;

        public Node(int data, Node next) {
            this.next = next;
            this.data = data;
        }
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
                continue;
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
        SinglyLinkedList linkedList =new SinglyLinkedList();
        linkedList.addLast(1);
        linkedList.addLast(2);
        linkedList.addLast(3);
        linkedList.addLast(2);
        linkedList.addLast(1);
        System.err.println("是否为回文:"+linkedList.palindrome());


        SinglyLinkedList linkedList1 =new SinglyLinkedList();
        linkedList1.addLast(1);
        linkedList1.addLast(1);
        linkedList1.addLast(4);
        linkedList1.addLast(2);
        linkedList1.addLast(1);
        System.err.println("linkedList1 是否为回文:"+linkedList1.palindrome());

        SinglyLinkedList linkedList2 =new SinglyLinkedList();
        linkedList2.addLast(1);
        linkedList2.addLast(4);
        linkedList2.addLast(4);
        linkedList2.addLast(1);
        System.err.println("linkedList2 是否为回文:"+linkedList2.palindrome());

        SinglyLinkedList linkedList3 =new SinglyLinkedList();
        linkedList3.addLast(1);
        linkedList3.addLast(4);
        linkedList3.addLast(4);
        linkedList3.addLast(2);
        System.err.println("linkedList3 是否为回文:"+linkedList3.palindrome());
    }
}
