package java_core.concurrent;

/**
 * @author:ben.gu
 * @Date:2019/3/11 8:21 PM
 */
public class DoubleCheckTest {
    private static class LazySomethingHolder{
        public static Something something = new Something();
    }

    public static Something getInstance(){
        return LazySomethingHolder.something;
    }

    public static void main(String args[]) {

        new DoubleCheckTest();

        Something instance = DoubleCheckTest.getInstance();

    }

}

class Something{
    public Something() {
        System.err.println("Something constructor");
    }
}