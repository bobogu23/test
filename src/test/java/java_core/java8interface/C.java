package java_core.java8interface;

/**
 * @author:ben.gu
 * @Date:2020/5/22 10:41 上午
 */
public class C /*extends D*/ implements A, B {

    public static void main(String[] args) {
        new C().hello();
    }

    @Override
    public void hello() {
        B.super.hello();
    }
}
