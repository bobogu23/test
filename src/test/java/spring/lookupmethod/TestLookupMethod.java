package spring.lookupmethod;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 方法注入,可以实现每次都获取新的prototype类型的对象
 * @author:ben.gu
 * @Date:2018/10/31 下午10:02
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:LookupMethod-ApplicationContext.xml"})
public class TestLookupMethod {

    @Test
    public void testLookUpMethod(){
        ApplicationContext ac =new ClassPathXmlApplicationContext("classpath:LookupMethod-ApplicationContext.xml");
        FruitPlate p1= (FruitPlate) ac.getBean("fruitPlate1");
        FruitPlate p2= (FruitPlate) ac.getBean("fruitPlate2");
        p1.getFruit();
        p2.getFruit();

    }

}
