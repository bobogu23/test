package netty.objectsize;

import io.netty.util.internal.InternalThreadLocalMap;
import jdk.nashorn.internal.ir.debug.ObjectSizeCalculator;
import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * @author:ben.gu
 * @Date:2020/1/7 2:17 PM
 */
public class InternalThreadLocalMapTest {
    private static Unsafe unsafe;

    static {
        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            unsafe = (Unsafe) field.get(null);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    public static void main(String args[]) {
        Field[] declaredFields1 = InternalThreadLocalMap.class.getSuperclass().getDeclaredFields();
        for (Field field : declaredFields1) {
            int modifiers = field.getModifiers();

            if(Modifier.isStatic(modifiers)){
                System.out.println("Superclass static field "+field.getName() + "---offSet:" + unsafe.staticFieldOffset(field));

            }else {
                System.out.println("Superclass "+ field.getName() + "---offSet:" + unsafe.objectFieldOffset(field));
            }

        }

        System.out.println("================================");


        Field[] declaredFields = InternalThreadLocalMap.class.getDeclaredFields();
        for (Field field : declaredFields) {
            int modifiers = field.getModifiers();

            if(Modifier.isStatic(modifiers)){
                System.out.println("static field "+field.getName() + "---offSet:" + unsafe.staticFieldOffset(field));

            }else {
                System.out.println(field.getName() + "---offSet:" + unsafe.objectFieldOffset(field));
            }

        }

        System.out.println("================================");

        long size = ObjectSizeCalculator.getObjectSize(InternalThreadLocalMap.get());
        System.err.println("InternalThreadLocalMap size :" + size);

    }

}
