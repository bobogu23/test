package spring.aop.advice;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author:ben.gu
 * @Date:2019/3/19 11:20 AM
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:AroundAdvice-ApplicationContext.xml"})
public class MethodInterceptorTest {

    @Autowired
    private HelloWorld helloWorldProxy;

    @Test
    public void test(){
        helloWorldProxy.say();
    }

}
