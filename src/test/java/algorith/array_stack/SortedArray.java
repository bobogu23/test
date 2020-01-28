package algorith.array_stack;

/**
 * 大小固定的有序数组，支持动态增删改操作
 * 默认从小到大.数组有序可以使用二分法查找
 *
 * 新增：
 *    需要找到合适的位置保证数组有序.
 *
 * 修改
 *    先删除指定位置上的元素,然后做新增操作 保证数组有序
 *
 * 删
 *   删除后将删除位置后的元素向前移动
 *
 * @author:ben.gu
 * @Date:2020/1/28 3:37 PM
 */
public class SortedArray {

    public static void main(String args[]) {

        SortedArray array = new SortedArray(12);
        array.add(0);
        array.add(2);
        array.add(1);
        array.add(10);
        array.add(9);
        array.add(10);
        array.add(99);
        array.add(10);
        array.add(99);
        array.add(98);
        array.add(97);
        array.add(90);
        array.printElements();
        array.remove(10);
        array.printElements();
        array.update(9, 91);
        array.printElements();

    }

    private int[] array;

    private int size = 0;

    public SortedArray(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("capacity must be positive");
        }
        this.array = new int[capacity];
    }

    public void update(int target, int newElem) {
        remove(target);
        add(newElem);
    }

    public void remove(int target) {
        int firstPosition = searchFirstEqualElemIndex(target);
        if (firstPosition == -1) {
            return;
        }
        int lastPosition = firstPosition;
        //只找到一个相同的元素
        if (firstPosition == size - 1) {
            array[firstPosition] = 0;
            size--;
        } else {
            lastPosition = searchLastEqualElemIndex(target);
            for (int i = firstPosition; i <= lastPosition; i++) {
                array[i] = 0;
            }
        }

        int span = lastPosition - firstPosition + 1;
        //删除元素后的元素往前移
        for (int i = firstPosition; i < size - span; i++) {
            array[i] = array[i + span];
        }
        size = size - span;
    }

    /**
     * 二分法查找第一个大于等于待插入元素的元素 .移动该元素及之后的数组元素,插入新的元素
     * @param elem
     */
    public void add(int elem) {
        if (size >= array.length) {
            return;
        }
        int position = searchFirstGreaterElemIndex(elem);
        //没找到比待插入元素大的元素,直接放在末尾
        if (position == -1) {
            array[size] = elem;

        } else {
            //移动position 及之后的元素
            for (int i = size; i > position; i--) {
                array[i] = array[i - 1];
            }
            array[position] = elem;
        }
        size++;
    }

    public void printElements() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < size; i++) {
            builder.append(array[i]).append(",");
        }
        String result = builder.deleteCharAt(builder.length() - 1).toString();
        System.err.println(result);
    }

    private int searchFirstGreaterElemIndex(int elem) {
        int low = 0;
        int high = size;

        while (low <= high) {
            //如果使用 (high+low)/2 可能出现整数溢出
            int mid = low + (high - low) / 2;

            if (array[mid] >= elem) {
                if (mid == 0 || array[mid - 1] < elem) {
                    return mid;
                }
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return -1;
    }

    private int searchFirstEqualElemIndex(int elem) {
        int low = 0;
        int high = size;

        while (low <= high) {
            //如果使用 (high+low)/2 可能出现整数溢出
            int mid = low + (high - low) / 2;

            if (array[mid] > elem) {
                high = mid - 1;
            } else if (array[mid] < elem) {
                low = mid + 1;
            } else {
                if (mid == 0 || array[mid - 1] != elem) {
                    return mid;
                }
                high = mid - 1;
            }
        }
        return -1;
    }

    private int searchLastEqualElemIndex(int elem) {
        int low = 0;
        int high = size;
        while (low <= high) {
            int mid = low + ((high - low) >> 1);
            if (array[mid] > elem) {
                high = mid - 1;
            } else if (array[mid] < elem) {
                low = mid + 1;
            } else {
                if (mid == size - 1 || array[mid + 1] != elem) {
                    return mid;
                }
                low = mid + 1;
            }
        }

        return -1;

    }

}
