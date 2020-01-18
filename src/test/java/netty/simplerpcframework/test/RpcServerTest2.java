package netty.simplerpcframework.test;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author:ben.gu
 * @Date:2020/1/7 10:08 PM
 */
public class RpcServerTest2 {

    public static void main(String args[]) {
        //启动第二个实例
        new ClassPathXmlApplicationContext("classpath:netty/RPC-Server-ApplicationContext-2.xml");
    }
}
