package java_core.string;

/**
 * @author:ben.gu
 * @Date:2019/6/22 10:04 AM
 */
public class StringTest {

    public static void main(String args[]) {
        String a = "1111";
        String b = "1111";
        System.err.println("a==b:" + (a == b));
        String c =new String(b);
        System.err.println("a==c:" + (a == c));
        System.err.println("b==c:" + (b == c));
        String d = c.intern();
        System.err.println("d==b:" + ( d== b));

    }
}
