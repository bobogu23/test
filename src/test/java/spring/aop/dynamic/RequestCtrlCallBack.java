package spring.aop.dynamic;

import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author:ben.gu
 * @Date:2019/3/13 12:25 AM
 */
public class RequestCtrlCallBack implements MethodInterceptor {
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        if (method.getName().equals("request")) {
            System.err.println("dynamic proxy");

            return methodProxy.invokeSuper(o, objects);

        }
        return null;
    }
}
