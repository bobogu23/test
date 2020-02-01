package algorith.queue;

import java.util.Arrays;

/**
 * 数组实现队列
 * @author:ben.gu
 * @Date:2020/2/1 8:35 PM
 */
public class ArrayQueue {

    public static void main(String args[]) {
        ArrayQueue q = new ArrayQueue(5);
        q.enqueue("a");
        q.enqueue("b");
        q.enqueue("c");
        q.enqueue("d");
        q.enqueue("e");
        q.enqueue("f");
        System.err.println(q.toString());

        q.dequeue();
        System.err.println(q.toString());

        q.dequeue();
        System.err.println(q.toString());
        q.dequeue();
        q.dequeue();
        q.dequeue();
        System.err.println(q.toString());
        q.dequeue();
        System.err.println(q.toString());

    }

    private String[] array;

    private int capacity;

    private int header;

    private int tail;

    @Override
    public String toString() {
        return "ArrayQueue{" + "array=" + Arrays.toString(array) + ", capacity=" + capacity + ", header=" + header
                + ", tail=" + tail + '}';
    }

    public ArrayQueue(int capacity) {
        this.capacity = capacity;
        this.header = 0;
        this.tail = 0;
        this.array = new String[capacity];
    }

    public boolean enqueue(String s) {
        if (tail == capacity) {
            //队列已满
            if (header == 0) {
                return false;
            }
            // header <-> tail => 0<-> tail-header
            for (int i = header; i < tail; i++) {
                array[i - header] = array[i];
            }
            tail = tail - header;
            header = 0;
            array[tail] = s;
            tail++;
            return true;
        }
        array[tail] = s;
        tail++;
        return true;
    }

    public String dequeue() {
        if (header == tail) {
            return null;
        }
        String h = array[header];
        array[header] = null;
        header++;
        return h;
    }
}
