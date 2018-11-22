package spring.beancreateprocess.beans;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author:ben.gu
 * @Date:2018/11/20 下午11:43
 */
public class Son implements ApplicationContextAware {
    private int age;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Son(int age) {
        this.age = age;
    }

    public Son() {
        System.out.println("构造器初始化");
        System.out.println("age:" + age);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

    }
}
