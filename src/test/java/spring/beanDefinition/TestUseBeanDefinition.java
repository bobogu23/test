package spring.beanDefinition;

import org.junit.Test;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.config.ConstructorArgumentValues;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * @author:ben.gu
 * @Date:2018/12/27 11:55 PM
 */
public class TestUseBeanDefinition {

    @Test
    public void testUseCreateBean() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        BeanFactory container = bindViaCode(beanFactory);
        CC cc = (CC) container.getBean("cc");
        cc.say();
    }

    private BeanFactory bindViaCode(BeanDefinitionRegistry registry) {
        AbstractBeanDefinition aa = new RootBeanDefinition(AA.class, AutowireCapableBeanFactory.AUTOWIRE_BY_TYPE,
                true);
        AbstractBeanDefinition bb = new RootBeanDefinition(BB.class, AutowireCapableBeanFactory.AUTOWIRE_BY_TYPE,
                true);
        AbstractBeanDefinition cc = new RootBeanDefinition(CC.class, AutowireCapableBeanFactory.AUTOWIRE_BY_TYPE,
                true);

        //bean定义注册到容器
        registry.registerBeanDefinition("aa", aa);
        registry.registerBeanDefinition("bb", bb);
        registry.registerBeanDefinition("cc", cc);

        //指定依赖关系

        //通过构造器注入
        ConstructorArgumentValues argumentValues = new ConstructorArgumentValues();
        argumentValues.addIndexedArgumentValue(0, aa);
        bb.setConstructorArgumentValues(argumentValues);

        //通过set方法注入
        MutablePropertyValues propertyValues = new MutablePropertyValues();
        propertyValues.addPropertyValue(new PropertyValue("bb", bb));

        cc.setPropertyValues(propertyValues);

        return (BeanFactory) registry;
    }
}
