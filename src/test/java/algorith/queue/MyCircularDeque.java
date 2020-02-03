package algorith.queue;

/**
 * @author:ben.gu
 * @Date:2020/2/2 10:02 PM
 *
 * /**
 * Your MyCircularDeque object will be instantiated and called as such:
 * MyCircularDeque obj = new MyCircularDeque(k);
 * boolean param_1 = obj.insertFront(value);
 * boolean param_2 = obj.insertLast(value);
 * boolean param_3 = obj.deleteFront();
 * boolean param_4 = obj.deleteLast();
 * int param_5 = obj.getFront();
 * int param_6 = obj.getRear();
 * boolean param_7 = obj.isEmpty();
 * boolean param_8 = obj.isFull();
 */

public class MyCircularDeque {

    private int[] array;

    private int start;

    private int end;

    private int count;

    private int capacity;

    /**
     * ["MyCircularDeque","insertLast","insertLast","insertFront","insertFront","getRear","isFull","deleteLast","insertFront","getFront"]
     [[3],[1],[2],[3],[4],[],[],[],[4],[]]
     */

    /** Initialize your data structure here. Set the size of the deque to be k. */
    public MyCircularDeque(int k) {
        array = new int[k];
        start = 0;
        end = 0;
        capacity = k;
    }

    /** Adds an item at the front of Deque. Return true if the operation is successful. */
    public boolean insertFront(int value) {
        if (count == capacity) {
            return false;
        }
        int s = (start - 1 + array.length) % (array.length);
        array[s] = value;
        start = s;
        count++;
        return true;
    }

    /** Adds an item at the rear of Deque. Return true if the operation is successful. */
    public boolean insertLast(int value) {
        if (count == capacity) {
            return false;
        }
        array[end] = value;
        end = (end + 1) % array.length;
        count++;
        return true;
    }

    /** Deletes an item from the front of Deque. Return true if the operation is successful. */
    public boolean deleteFront() {
        if (count == 0) {
            return false;
        }
        array[start] = 0;
        start = (start + 1) % (array.length);
        count--;
        return true;
    }

    /** Deletes an item from the rear of Deque. Return true if the operation is successful. */
    public boolean deleteLast() {
        if (count == 0) {
            return false;
        }
        end = (end - 1 + array.length) % (array.length);
        array[end] = 0;
        count--;
        return true;

    }

    /** Get the front item from the deque. */
    public int getFront() {
        if (isEmpty()) {
            return -1;
        }
        return array[start];
    }

    /** Get the last item from the deque. */
    public int getRear() {
        if (isEmpty()) {
            return -1;
        }
        return array[(end - 1 + array.length) % (array.length)];
    }

    /** Checks whether the circular deque is empty or not. */
    public boolean isEmpty() {
        return count == 0;
    }

    /** Checks whether the circular deque is full or not. */
    public boolean isFull() {
        return count == capacity;
    }

    public static void main(String args[]) {
        MyCircularDeque queue = new MyCircularDeque(3);
        boolean b = queue.insertFront(9);
        System.err.println(b);
        System.err.println(queue.getRear());
        b = queue.insertFront(9);
        System.err.println(b);
        System.err.println(queue.getRear());
        b = queue.insertLast(5);
        System.err.println(b);
        System.err.println(queue.getFront());
        System.err.println(queue.getRear());
        System.err.println(queue.getFront());
        System.err.println(queue.insertLast(8));
        System.err.println(queue.deleteLast());
        System.err.println(queue.getFront());

        //        ["MyCircularDeque","insertLast","insertLast","insertFront","insertFront","getRear","isFull","deleteLast","insertFront","getFront"]
        //[[3],[1],[2],[3],[4],[],[],[],[4],[]]
        //[null,true,true,true,false,2,true,true,true,4]

        //["MyCircularDeque","insertFront","getRear","insertFront","getRear","insertLast","getFront","getRear","getFront","insertLast","deleteLast","getFront"]
        //[[3],[9],[],[9],[],[5],[],[],[],[8],[],[]]
    }
}
