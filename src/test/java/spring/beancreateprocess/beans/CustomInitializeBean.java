package spring.beancreateprocess.beans;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.DestructionAwareBeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author:ben.gu
 * @Date:2018/12/2 2:58 PM
 */
public class CustomInitializeBean implements BeanNameAware, BeanFactoryAware, BeanPostProcessor, InitializingBean,
        DisposableBean, DestructionAwareBeanPostProcessor, ApplicationContextAware {

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        System.out.println("BeanFactoryAware setBeanFactory...");
    }

    @Override
    public void setBeanName(String name) {
        System.out.println("BeanNameAware setBeanName,name:" + name);

    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("InitializingBean afterPropertiesSet");

    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof Son) {
            System.out.println("BeanPostProcessor  son  postProcessBeforeInitialization");

            Son son = (Son) bean;
            son.setAge(75);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof Son) {
            System.out.println("BeanPostProcessor son postProcessAfterInitialization");

            Son son = (Son) bean;
            son.setAge(71);
        }
        return bean;
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("DisposableBean destroy");

    }

    @Override
    public void postProcessBeforeDestruction(Object bean, String beanName) throws BeansException {
        System.out.println(beanName + " DestructionAwareBeanPostProcessor postProcessBeforeDestruction");
    }

    @Override
    public boolean requiresDestruction(Object bean) {
        return false;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("ApplicationContextAware setApplicationContext");
    }
}
