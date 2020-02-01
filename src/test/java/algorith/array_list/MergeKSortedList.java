package algorith.array_list;

/**
 * Merge k sorted linked lists and return it as one sorted list. Analyze and describe its complexity.

 Example:

 Input:
 [
 1->4->5,
 1->3->4,
 2->6
 ]
 Output: 1->1->2->3->4->4->5->6
 * @author:ben.gu
 * @Date:2020/1/31 9:46 PM
 */
public class MergeKSortedList {

    public static void main(String args[]) {
        ListNode[] lists = new ListNode[3];
        ListNode l1 = new ListNode(1);
        ListNode l11 = new ListNode(4);
        l1.next = l11;
        ListNode l12 = new ListNode(5);
        l11.next = l12;
        lists[0] = l1;

        ListNode l2 = new ListNode(1);
        ListNode l21 = new ListNode(3);
        l2.next = l21;
        ListNode l22 = new ListNode(4);
        l21.next = l22;
        lists[1] = l2;

        ListNode l3 = new ListNode(2);
        ListNode l31 = new ListNode(6);
        l3.next = l31;

        lists[2] = l3;

        MergeKSortedList list = new MergeKSortedList();
        ListNode listNode = list.mergeKLists(lists);
        listNode.print();

    }

    static public class ListNode {
        int val;

        ListNode next;

        ListNode(int x) {
            val = x;
        }

        public void print() {
            StringBuilder sb = new StringBuilder().append(this.val).append(",");
            ListNode next = this.next;
            while (next != null) {
                sb.append(next.val).append(",");
                next = next.next;
            }

            System.err.println(sb.deleteCharAt(sb.length() - 1).toString());
        }
    }

    //分治法,先合并2个链表，得到的新链表 跟第三个链表合并 以此类推
    //时间复杂度 2个链表合并复杂度 n(元素个数),k个链表 两两合并复杂度 log^k,合起来 nlog^k
    //也可以用归并，将k个链表 用二分法一直分割，最终分割到1个链表，与另一个链表合并
    public ListNode mergeKLists(ListNode[] lists) {
        if (lists == null || lists.length == 0) {
            return null;
        }
        if (lists.length == 1) {
            return lists[0];
        }
        int left = 0;
        int right = lists.length - 1;

        //合并的结果放到left所在位置
        // 如果 lists 有偶数个链表。left+1=right 时,左右两个下标碰头，left 回到0,right--，继续循环跟left合并
        //如果lists 有奇数个链表.left+2=right 时,left ，right 无法同时移动,left 回到0，right--，继续循环跟left合并
        while (left < right) {
            lists[left] = merge2List(lists[left], lists[right]);
            if (left + 1 == right) {
                left = 0;
                right--;
            } else if (left + 2 == right) {
                left = 0;
                right--;
            } else {
                left++;
                right--;
            }
        }
        return lists[0];
    }

    private ListNode merge2List(ListNode list, ListNode list1) {
        //哨兵节点,他的next节点才是头节点
        ListNode l1 = new ListNode(0);
        //指向l1 尾节点的指针
        ListNode tmp = l1;
        while (list != null && list1 != null) {
            if (list.val < list1.val) {
                tmp.next = list;
                list = list.next;
            } else {
                tmp.next = list1;
                list1 = list1.next;
            }
            tmp = tmp.next;
        }

        if (list != null) {
            tmp.next = list;
        } else {
            tmp.next = list1;
        }

        return l1.next;
    }

}
