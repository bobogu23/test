package spring.beancreateprocess.beans;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * @author:ben.gu
 * @Date:2018/11/23 下午1:49
 */
public class ImplementBeanPostProcessor implements BeanPostProcessor {
    @Override public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println(getClass().getName() +"->"+beanName+" postProcessBeforeInitialization");
        return bean;
    }

    @Override public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println(getClass().getName() +"->"+beanName+" postProcessAfterInitialization");
        return bean;
    }
}
