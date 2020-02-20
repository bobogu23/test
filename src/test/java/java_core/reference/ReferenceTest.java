package java_core.reference;

import java.lang.ref.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author:ben.gu
 * @Date:2020/2/17 10:56 PM
 */
public class ReferenceTest {

    private static List<Reference> roots = new ArrayList<>();

    public static void main(String args[]) throws InterruptedException {

        ReferenceQueue queue = new ReferenceQueue();
        new Thread(() -> {
            int i = 0;
            while (true) {
                try {
                    Reference ref = queue.remove();
                    System.err.println("reference:" + ref);
                    System.err.println("result:" + ref.get());
                    System.err.println("remove queue num:" + ++i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }).start();

        for (int i = 0; i < 10000; i++) {
            Reference<Object> reference = new SoftReference<Object>(new byte[1024 * 1024], queue);
//            Reference<Object> reference = new WeakReference<Object>(new byte[1024 * 1024], queue);
//            Reference<Object> reference = new PhantomReference<>(new byte[1024 * 1024], queue);
            roots.add(reference);
            System.gc();
            System.err.println("produce:" + i);
            Thread.sleep(100);

        }

    }
}
