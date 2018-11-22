package spring.beancreateprocess;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import spring.beancreateprocess.beans.ImplementBeanFactoryPostProcessor;
import spring.beancreateprocess.beans.Son;

/**
 * @author:ben.gu
 * @Date:2018/11/20 下午10:56
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:CreateBeanProcess-ApplicationContext.xml"})
public class TestBeanCreateProcess {
    @Autowired
    private ApplicationContext applicationContext;

    /**
     * 测试使用BeanFactoryPostProcessor 修改bean属性值。
     * 在AbstractAutowireCapableBeanFactory 的doCreateBean方法中创建bean。创建完成之后
     * 在初始化bean应用bean修改后的属性值(populateBean方法).
     *
     * <blockquote><pre> Object exposedObject = bean;
     try {
     //填充属性值,
     populateBean(beanName, mbd, instanceWrapper);
     if (exposedObject != null) {
     //调用各种回调口子初始化
     exposedObject = initializeBean(beanName, exposedObject, mbd);
     }
     }
     </blockquote></pre>
     */
    @Test
    public void  testApplyBeanFactoryPostProcessor(){
        Son bean = (Son) applicationContext
                .getBean("spring.beancreateprocess.beans.Son#0");
        System.out.println("getAge:"+bean.getAge());
    }



}
