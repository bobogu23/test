package spring.aop.invocationhandler;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author:ben.gu
 * @Date:2019/3/13 12:04 AM
 */
public class RequestCtrInvocationHandler implements InvocationHandler {
    private Object target;

    public RequestCtrInvocationHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if(method.getName().equals("request")){
            System.err.println("proxy object class name:"+proxy.getClass().getCanonicalName());
            System.err.println("invocation handler");

            return method.invoke(target,args);
        }
        return null;
    }
}
