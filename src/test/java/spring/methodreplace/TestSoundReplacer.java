package spring.methodreplace;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author:ben.gu
 * @Date:2019/3/5 8:17 AM
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:MethodReplacer-ApplicationContext.xml"})
public class TestSoundReplacer  {
    @Autowired
    private Pig pig;

    @Test
    public void test(){
        pig.sound();
    }
}
