package java_core.java8interface;

/**
 * @author:ben.gu
 * @Date:2020/5/22 10:41 上午
 */
public interface A {
    default void hello() {
        System.out.println("hello from A");
    }
}
