package java_core.thread;

import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * Fork/Join 测试
 * jdk7引入，任务拆分 合并
 * @author:ben.gu
 * @Date:2019/4/29 10:31 PM
 */
public class ForkJoinTest {

    static  class SumTask extends RecursiveTask<Long> {
        static final int threshold = 100;

        private int start;

        private int end;

        private int[] array;

        public SumTask(int start, int end, int[] array) {
            this.start = start;
            this.end = end;
            this.array = array;
        }

        @Override
        protected Long compute() {
            System.out.println("current thread name:"+Thread.currentThread().getName());
            if (end - start <= threshold) {
                long sum = 0;
                for (int i = start; i < end; i++) {
                    sum += array[i];
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(String.format("compute %d~%d = %d", start, end, sum));
                return sum;

            }

            //数量超过阈值，拆分任务
            int middle = (start + end) / 2;
            System.out
                    .println(String.format("split %d~%d ==> %d~%d, %d~%d", start, end, start, middle, middle, end));
            SumTask s1 = new SumTask(start, middle, array);
            SumTask s2 = new SumTask(middle, end, array);
            invokeAll(s1, s2);
            Long r1 = s1.join();
            Long r2 = s2.join();
            Long result = r1 + r2;
            System.out.println("result = " + r1 + " + " + r2 + " ==> " + result);
            return result;
        }
    }

    public static void main(String args[]) {
//
//        int[] array = new int[400];
//
//        for (int i = 0; i < 400; i++) {
//            array[i] = new Random().nextInt(400);
//        }
//
//        // fork/join task:
//        ForkJoinPool fjp = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
//
//        long startTime =System.currentTimeMillis();
//        Long result = fjp.invoke(new SumTask(0, 400, array));
//        long endTime =System.currentTimeMillis();
//        System.out.println("Fork/join sum: " + result + " in " + (endTime - startTime) + " ms.");
        int i = (-1 << 29 )| 0;
        System.err.println("i:"+i);

        int worker = i & ((1 << 29) - 1);
        System.err.println("worker:"+worker);

        int worker1 = (i +1) & ((1 << 29) - 1);
        System.err.println("worker1:"+worker1);

        Integer num = null;
        Integer integer = Optional.ofNullable(num).orElse(0);
        System.err.println("integer:"+integer);
        num = 4;
        integer = Optional.ofNullable(num).orElse(0);
        System.err.println("integer:"+integer);


    }

}
