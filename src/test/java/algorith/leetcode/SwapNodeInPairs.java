package algorith.leetcode;

/**
 * https://leetcode.com/problems/swap-nodes-in-pairs/description/
 * @author:ben.gu
 * @Date:2020/5/11 10:16 下午
 */
public class SwapNodeInPairs {

    public static void main(String[] args) {
        ListNode n1 = new ListNode(1);
        ListNode n2 = new ListNode(2);
        ListNode n3 = new ListNode(3);
        ListNode n4 = new ListNode(4);

        n1.next = n2;
        n2.next = n3;
        n3.next = n4;
        SwapNodeInPairs p = new SwapNodeInPairs();

        ListNode r = p.swapPairs(n1);

        System.err.println("r:" + r);
    }

    public static class ListNode {
        int val;

        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }

        @Override
        public String toString() {
            return "ListNode{" + "val=" + val + ", next=" + (next != null ? next.toString() : "" )+ '}';
        }
    }

    //Given 1->2->3->4, you should return the list as 2->1->4->3.
    public ListNode swapPairs(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode sentinel = new ListNode(0);
        sentinel.next = head;
        ListNode first = sentinel;
        ListNode second = sentinel;

        int count = 0;
        while (first != null) {
            if (count > 0 && count % 2 == 0) {
                second.next.next = first.next;
                first.next = second.next;
                second.next = first;
                first = second.next.next;
                second = second.next.next;
            }
            first = first.next;
            count++;
        }

        return sentinel.next;
    }
}
