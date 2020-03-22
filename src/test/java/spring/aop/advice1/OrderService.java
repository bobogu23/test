package spring.aop.advice1;


/**
 * @author:ben.gu
 * @Date:2020/3/14 10:04 AM
 */
public interface OrderService {
    Order createOrder(String username, String product);

    Order queryOrder(String username);
}
