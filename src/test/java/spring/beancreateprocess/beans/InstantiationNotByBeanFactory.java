package spring.beancreateprocess.beans;

import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;

import java.beans.PropertyDescriptor;

/**
 * @author:ben.gu
 * @Date:2018/11/30 1:17 PM
 */
public class InstantiationNotByBeanFactory implements InstantiationAwareBeanPostProcessor {

    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        if(beanClass.isAssignableFrom(Son.class)){
            try {
                System.out.println("son postProcessBeforeInstantiation");
                 return beanClass.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
        if(bean instanceof  Son){
            System.out.println("son postProcessAfterInstantiation");
            Son son = (Son) bean;
            son.setAge(88);
        }
        return true;
    }

    @Override
    public PropertyValues postProcessPropertyValues(PropertyValues pvs, PropertyDescriptor[] pds, Object bean,
            String beanName) throws BeansException {
        return pvs;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if(bean instanceof  Son){
            System.out.println("son postProcessBeforeInitialization");

            Son son = (Son) bean;
            son.setAge(78);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if(bean instanceof  Son){
            System.out.println("son postProcessAfterInitialization");
            Son son = (Son) bean;
            son.setAge(99);
        }

        return bean;
    }
}
