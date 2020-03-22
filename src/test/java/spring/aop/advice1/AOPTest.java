package spring.aop.advice1;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author:ben.gu
 * @Date:2020/3/14 2:35 PM
 */
public class AOPTest {

    public static void main(String args[]) {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:Advice1-ApplicationContext.xml");
        UserService userService = context.getBean(UserService.class);
        OrderService orderService = context.getBean(OrderService.class);

        userService.createUser("Tom", "Cruise", 55);
        userService.queryUser();

        orderService.createOrder("Leo", "随便买点什么");
        orderService.queryOrder("Leo");

    }
}
