package algorith.linkedlist;

/**
 * 跳跃表
 * 多层链表组成，最底层包含所有元素 而且有序。
n * 每一层元素都有序。
 * 每个节点有多个指针，一个指向前一个节点的指针。
 * @author:ben.gu
 * @Date:2019/10/19 8:58 PM
 */
public class SkipList {

    public static void main(String args[]) {
        SkipList list = new SkipList();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(10);
        list.add(6);
        list.add(90);
        list.add(40);
        list.add(30);
        list.printAll();

        Node node = list.find(100);
        System.err.println("node->" + node);
        node = list.find(10);
        System.err.println("node->" + node);

        list.delete(10);
        list.printAll();

        list.delete(100);
        list.printAll();

    }

    /**
     * 头节点,不保存数据
     */
    private Node header = new Node();

    private int levelCount = 1;

    private static final int max_level = 16;

    private static final float skiplist_p = 0.5F;

    static class Node {
        private int data = -1;

        private Node[] next;

        private int maxLevel = 0;

        public Node() {
            this.next = new Node[max_level];
            this.maxLevel = max_level;
        }

        public Node(int maxLevel) {
            this.maxLevel = maxLevel;
            this.next = new Node[maxLevel];
        }

        @Override
        public String toString() {
            return String.format("{ data: %d; levels: %d }", data, maxLevel);
        }
    }

    public void printAll() {
        Node h = header;
        while (h.next[0] != null) {
            System.err.print(h.next[0] + " ");
            h = h.next[0];
        }
        System.err.println();
    }

    /**
     * 找到最大的小于插入数字的所有节点，新插入的节点 next节点指向将这些节点的下一个节点,这些节点的指向新加入的节点
     *
     * @param data
     */
    public void add(int data) {

        //随机分配层级
        int level = randomLevel();
        Node newNode = new Node(level);

        newNode.data = data;

        //每一层从头节点开始遍历
        Node[] update = new Node[level];
        for (int i = 0; i < level; i++) {
            update[i] = header;
        }

        /**
         * 从顶层开始查找。找出每一层小于插入数据的最大数据
         */
        Node p = header;
        for (int i = level - 1; i >= 0; i--) {
            while (p.next[i] != null && p.next[i].data < data) {
                p = p.next[i];
            }
            update[i] = p;
        }

        //新节点插入
        for (int i = 0; i < level; i++) {
            newNode.next[i] = update[i].next[i];
            update[i].next[i] = newNode;
        }

        if (levelCount < level) {
            levelCount = level;
        }
    }

    /**
     * 从头节点 顶层开始遍历
     * @param value
     * @return
     */
    public Node find(int value) {
        Node p = header;
        for (int i = p.maxLevel - 1; i >= 0; i--) {
            while (p.next[i] != null && p.next[i].data < value) {
                p = p.next[i];
            }
            if (p.next[i] != null && p.next[i].data == value) {
                return p.next[i];
            }
        }
        return null;
    }

    private int randomLevel() {
        int level = 1;
        while (Math.random() < skiplist_p && level < max_level) {
            level += 1;
        }
        return level;
    }

    /**
     * 删除指定数据,从头节点最高层开始遍历，找到之后 每层都要做删除
     * @param value
     */
    public void delete(int value) {
        //找到小于指定数据的最大节点
        Node[] tmp = new Node[levelCount];
        Node p = header;

        for (int i = levelCount - 1; i >= 0; i--) {
            while (p.next[i] != null && p.next[i].data < value) {
                p = p.next[i];
            }
            tmp[i] = p;
        }

        //找到了要删除的节点
        if (p.next[0] != null && p.next[0].data == value) {
            for (int i = levelCount - 1; i >= 0; i--) {
                if (tmp[i].next[i] != null && tmp[i].next[i].data == value) {
                    tmp[i].next[i] = tmp[i].next[i].next[i];
                }

            }
        }
        //层高计算
        while (levelCount > 1 && header.next[levelCount] == null) {
            levelCount--;
        }

    }
}
