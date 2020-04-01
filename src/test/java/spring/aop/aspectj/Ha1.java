package spring.aop.aspectj;

import org.springframework.stereotype.Component;

/**
 * @author:ben.gu
 * @Date:2020/4/1 5:14 PM
 */
@Component
public class Ha1 {
    public void hello(){
        System.err.println("Ha1 hello...");
    }

    public void hello1(){
        System.err.println("Ha1 hello1...");
    }
}
