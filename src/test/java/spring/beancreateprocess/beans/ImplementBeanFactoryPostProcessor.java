package spring.beancreateprocess.beans;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

/**
 * @author:ben.gu
 * @Date:2018/11/20 下午11:10
 */
public class ImplementBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        System.out.println(getClass().getName()+"->"+" invoke  postProcessBeanFactory...");
        BeanDefinition beanDefinition = beanFactory
                .getBeanDefinition("spring.beancreateprocess.beans.Son#0");
        beanDefinition.getPropertyValues().add("age",100);
        System.out.println("finish postProcessBeanFactory ...");
    }


}
