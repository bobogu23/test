package algorith.linkedlist;

/**
 * 约瑟夫环。
 * N个人围成圈,从1开始报数,报到M(M<N)的人出局，求最后一个人所在位置。
 * 
 * 使用单向循环链表，链表中记录每个人的编号。数到M的节点 删除，从m+1开始从1计数,一直到只剩一个节点,也就是该节点的next节点为null.
 * 
 * 编号
 * 1,2,3...m,m+1...n
 * 第一次循环
 * 1,2,3...m
 * 第二次循环
 * m+1...n..1..1+(n-(m+1)+1)
 * @author:ben.gu
 * @Date:2020/2/5 10:26 AM
 */
public class JosonfLoop {
    private Person first;

    private int capacity;

    public static void main(String args[]) {
        JosonfLoop l = new JosonfLoop(10);
        l.print();
        int num = l.lastPersonNum(3);
        //1,2,3,4,5,6,7,8,9,10
        //1,3,5,7,9,
        //1,5,9
        //5


//        1,2,4,5,7,8,10  10,1,4,5,8,10,4,5,10,4,10,4 4


        System.err.println("num:" + num);
    }

    public JosonfLoop(int num) {
        Person curr = null;
        for (int i = 1; i <= num; i++) {
            if (first == null) {
                first = new Person(i, null);
                first.next = first;
                curr = first;
            } else {
                Person p = new Person(i, first);
                curr.next = p;
                curr = p;
            }
        }
        this.capacity = num;
    }

    private void print() {
        Person p = first;
        StringBuilder sb = new StringBuilder().append(p.no).append(",");
        while (p.next != null && p.next != first) {
            sb.append(p.next.no).append(",");
            p = p.next;
        }
        System.err.println("nums:" + sb.deleteCharAt(sb.length() - 1).toString());
    }

    public int lastPersonNum(int skipNum) {
        if (skipNum > capacity) {
            return -1;
        }
        int count = 1;
        Person p = first;
        while (p != null && p.next != p) {
            Person pre = p;
            while (p != null && count++ < skipNum) {
                pre = p;
                p = p.next;
            }
            //delete p
            pre.next = p.next;
            p.next = null;
            p = pre.next;
            count = 1;
        }

        return p.no;

    }

    static class Person {
        private int no;

        private Person next;

        public Person(int no, Person next) {
            this.no = no;
            this.next = next;
        }
    }

}
