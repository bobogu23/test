package spring.beancreateprocess.beans;

import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.beans.PropertyDescriptor;

/**
 * @author:ben.gu
 * @Date:2018/11/20 下午11:43
 */
public class Son implements ApplicationContextAware  {
    private int age;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        System.out.println("set age:" + age);
        this.age = age;
    }

    public Son(int age) {
        this.age = age;
    }

    public Son() {
        System.out.println("构造器初始化");
        System.out.println("age:" + age);
    }


    public void init (){
        System.out.println("son init");

    }
    public void destroy (){
        System.out.println("son destroy");

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("setApplicationContext...");
    }

}
