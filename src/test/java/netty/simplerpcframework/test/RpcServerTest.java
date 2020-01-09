package netty.simplerpcframework.test;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author:ben.gu
 * @Date:2020/1/7 10:08 PM
 */
public class RpcServerTest {

    public static void main(String args[]) {

        new ClassPathXmlApplicationContext("classpath:netty/RPC-Server-ApplicationContext.xml");
    }
}
