package spring.methodreplace;

import org.springframework.beans.factory.support.MethodReplacer;

import java.lang.reflect.Method;

/**
 * @author:ben.gu
 * @Date:2019/3/5 8:12 AM
 */
public class SoundReplacer implements MethodReplacer {

    @Override
    public Object reimplement(Object obj, Method method, Object[] args) throws Throwable {
        System.err.println("before execute method :"+method.getName()+" on object:"+obj.getClass().getName());

        System.err.println("haha");

        System.err.println("after execute method :"+method.getName()+" on object:"+obj.getClass().getName());

        return null;
    }
}
