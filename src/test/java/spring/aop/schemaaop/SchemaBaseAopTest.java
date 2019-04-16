package spring.aop.schemaaop;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author:ben.gu
 * @Date:2019/4/8 11:54 PM
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:SchemaAOP-ApplicationContext.xml"})
public class SchemaBaseAopTest {
    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private  Hallo hallo ;

    @org.junit.Test
    public void test(){
        Hallo hallo = (Hallo) applicationContext.getBean("hallo");
        hallo.say();
    }

    @Test
    public void test1(){
        hallo.say();
    }
}
