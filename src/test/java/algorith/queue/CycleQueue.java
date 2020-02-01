package algorith.queue;

import java.util.Arrays;

/**
 * 环形队列.
 * tail -> header
 * @author:ben.gu
 * @Date:2020/2/1 10:16 PM
 */
public class CycleQueue {

    public static void main(String args[]) {
        CycleArrayQueue aq = new CycleArrayQueue(5);
        aq.enqueue("1");
        aq.enqueue("2");
        aq.enqueue("3");
        aq.enqueue("4");
        aq.enqueue("5");

        System.err.println(aq.toString());
        aq.enqueue("6");
        System.err.println(aq.toString());
        aq.dequeue();
        System.err.println(aq.toString());
        aq.dequeue();
        aq.dequeue();
        aq.dequeue();
        aq.dequeue();
        System.err.println(aq.toString());
        aq.dequeue();
        System.err.println(aq.toString());

        aq.enqueue("a");
        aq.enqueue("b");
        aq.enqueue("c");
        aq.enqueue("d");
        aq.enqueue("e");
        System.err.println(aq.toString());


    }

    static class CycleArrayQueue {
        private String[] array;

        private int header;

        private int tail;

        private int capacity;

        @Override
        public String toString() {
            return "CycleArrayQueue{" + "array=" + Arrays.toString(array) + ", header=" + header + ", tail=" + tail
                    + ", capacity=" + capacity + '}';
        }

        public CycleArrayQueue(int capacity) {
            this.array = new String[capacity];
            this.capacity = capacity;
            this.header = 0;
            this.tail = 0;
        }

        public boolean enqueue(String s) {
            //            (tail+1)%capacity = header 队列满了.牺牲一个存储空间
            if ((tail + 1) % capacity == header) {
                return false;
            }
            array[tail] = s;
            tail = (tail+1) % capacity;
            return true;
        }

        public String dequeue() {
            if (header == tail) {
                return null;
            }
            String h = array[header];
            array[header] = null;
            header = (header+1) % capacity;
            return h;
        }
    }

}
