package spring.aop.advice;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * 1.0版本的AOP，通过ProxyFactoryBean 创建代理对象 Interceptor 指定advice
 * around advice
 * @author:ben.gu
 * @Date:2019/3/19 11:18 AM
 */
public class DiscountMethodInterceptor implements MethodInterceptor {
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        System.err.println("lalala method intercept");
        //获取参数
        invocation.getArguments();
        return invocation.proceed();
    }
}
