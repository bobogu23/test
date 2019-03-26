package spring.aop.dynamic;

import org.junit.Test;
import org.springframework.cglib.proxy.Enhancer;

/**
 * cglib 动态代理
 * @author:ben.gu
 * @Date:2019/3/13 12:27 AM
 */
public class DynamicTest  {

    @Test
    public void test(){
        Enhancer enhancer = new Enhancer();
        enhancer.setCallback(new RequestCtrlCallBack());
        enhancer.setSuperclass(Requestable.class);

        Requestable requestable = (Requestable) enhancer.create();
        requestable.request();


    }
}
