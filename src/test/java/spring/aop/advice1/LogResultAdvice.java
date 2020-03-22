package spring.aop.advice1;

import org.springframework.aop.AfterReturningAdvice;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @author:ben.gu
 * @Date:2020/3/14 2:13 PM
 */
public class LogResultAdvice implements AfterReturningAdvice {
    @Override
    public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
        System.out.println(method.getName() + "方法返回：" + returnValue);
    }
}
