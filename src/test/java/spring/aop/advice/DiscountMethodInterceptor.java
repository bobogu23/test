package spring.aop.advice;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * around advice
 * @author:ben.gu
 * @Date:2019/3/19 11:18 AM
 */
public class DiscountMethodInterceptor implements MethodInterceptor {
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        System.err.println("lalala method intercept");
        return invocation.proceed();
    }
}
