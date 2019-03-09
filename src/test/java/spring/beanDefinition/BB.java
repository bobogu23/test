package spring.beanDefinition;

/**
 * @author:ben.gu
 * @Date:2018/12/28 12:01 AM
 */
public class BB {
    private AA a;

    public BB(AA a) {
        this.a = a;
        System.err.println("create bb");
    }
}
