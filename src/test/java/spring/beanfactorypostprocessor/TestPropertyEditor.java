package spring.beanfactorypostprocessor;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author:ben.gu
 * @Date:2019/3/6 10:17 PM
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:PropertyEditor-ApplicationContext.xml"})
public class TestPropertyEditor {
    @Autowired
    private DateFoo dateFoo;

    @Test
    public void test(){
        System.err.println( dateFoo.getDate().toString());
    }

}




