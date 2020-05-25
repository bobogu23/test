package algorith.leetcode;

import org.aspectj.weaver.loadtime.definition.Definition;

import java.util.Stack;

/**
 * Given a linked list, remove the n-th node from the end of list and return its head.
 *
 * Example:
 *
 * Given linked list: 1->2->3->4->5, and n = 2.
 *
 * After removing the second node from the end, the linked list becomes 1->2->3->5. Note:
 *
 * Given n will always be valid.
 *
 * Follow up:
 *
 * Could you do this in one pass?
 * https://leetcode.com/problems/remove-nth-node-from-end-of-list/description/
 * @author:ben.gu
 * @Date:2020/5/7 10:21 下午
 */
public class RemoveNthNode {

    public static void main(String[] args) {
        ListNode n = new ListNode(1);
        n.next = new ListNode(2);
//        n.next.next = new ListNode(3);
//        n.next.next.next = new ListNode(4);
//        n.next.next.next.next = new ListNode(5);

        RemoveNthNode r = new RemoveNthNode();
        ListNode listNode = r.removeNthFromEnd2(n, 2);

    }

    /**
     * [1,2,3,4,5]
     * 2
     * @param head
     * @param n
     * @return
     */
    //两个指针,第一个先走N步，然后第一个，第二个指针同时移动，直到第一个指针移到链表末尾,
    public ListNode removeNthFromEnd2(ListNode head, int n) {
        ListNode sentinel = new ListNode(0);
        sentinel.next = head;
        ListNode first = sentinel;
        ListNode second = sentinel;
        for (int i = 0; i < n; i++) {
            first = first.next;
        }

        while (first.next != null) {
            first = first.next;
            second = second.next;
        }
        second.next = second.next.next;
        return sentinel.next;
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
    }

    public ListNode removeNthFromEnd(ListNode head, int n) {
        if (head == null) {
            return head;
        }
        Stack<ListNode> stack = new Stack<>();
        ListNode cur = head;
        while (cur != null) {
            stack.push(cur);
            cur = cur.next;
        }

        for (int i = 0; i < n; i++) {
            if (!stack.isEmpty()) {
                cur = stack.pop();
            }
        }
        ListNode pre = null;
        if (cur != null) {
            if (stack.isEmpty()) {
                head = cur.next;
            } else {
                pre = stack.pop();
                pre.next = cur.next;
            }
        }
        return head;
    }

}
