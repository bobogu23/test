package spring.aop.advice;

/**
 * @author:ben.gu
 * @Date:2019/3/19 11:59 AM
 */
public class HelloWorldImpl implements HelloWorld{
    @Override
    public void say() {
        System.err.println("hallo");
    }
}
