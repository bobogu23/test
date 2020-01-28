package algorith.array_stack;

import java.util.Arrays;

/**
 * @author:ben.gu
 * @Date:2020/1/28 2:56 PM
 */
public class DynamicArrayTest {

    //动态扩容数组

    public static class DynamicArray {

        private int[] array;

        private static final int INITIAL_SIZE = 10;

        private int index = 0;

        public void add(int elem) {
            if (array == null) {
                array = new int[INITIAL_SIZE];
            }
            if (index >= array.length) {
                //数组扩容
                int[] new_array = new int[array.length * 2];
                for (int i = 0; i < array.length; i++) {
                    new_array[i] = array[i];
                }
                array = new_array;
            }
            array[index] = elem;
            index++;

        }

        public int elementAt(int index) {
            return array[index];
        }

        public int size() {
            return index;
        }
    }

    public static void main(String args[]) {
        DynamicArray array = new DynamicArray();
        for (int i = 0; i < 100; i++) {
            array.add(i);
            System.err.println("array length:" + array.size());
        }

    }

}
