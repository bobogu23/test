package java_core.unsafe;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * @author:ben.gu
 * @Date:2019/5/20 9:15 PM
 */
public class UnSafeTest {

    public static void main(String args[]) throws Exception {
        Field unsafeField = Unsafe.class.getDeclaredFields()[0];
        unsafeField.setAccessible(true);
        Unsafe u = (Unsafe) unsafeField.get(null);

        Class<?> aClass = A.class;
        A a = new A();

        long bOffset = u.objectFieldOffset(aClass.getDeclaredField("b"));
        System.err.println("b=" + u.getInt(a, bOffset));

        Object staticBase = u.staticFieldBase(aClass.getDeclaredField("a"));
        long a1 = u.staticFieldOffset(aClass.getDeclaredField("a"));
        System.err.println("a=" + u.getInt(staticBase, a1));
    }

}

class A {
    private static int a = 123;

    private int b = 324;

    private final int c = 455;

    private static final int d = 444;
}