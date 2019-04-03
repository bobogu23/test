package spring.aop.aspectj;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.junit.Test;
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;

/**
 * @author:ben.gu
 * @Date:2019/3/28 10:10 PM
 */
@Aspect
public class AspectJTest {

    @Pointcut("execution(public void *.hello())) || execution(public void *.hello1()))")
    public void pointcutName(){
    }


    @Around("pointcutName()")
    public Object advice(ProceedingJoinPoint joinPoint){

        System.err.println("before execute ,haha...");
        try {
            return joinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return null;
    }


    class Ha1{
        public void hello(){
            System.err.println("Ha1 hello...");
        }

        public void hello1(){
            System.err.println("Ha1 hello1...");
        }
    }

    @Test
   public void test(){
        AspectJProxyFactory factory = new AspectJProxyFactory();
        factory.setProxyTargetClass(true);
        factory.setTarget(new Ha1());
        factory.addAspect(AspectJTest.class);

        Object proxy = factory.getProxy();

        ((Ha1)proxy).hello();
        ((Ha1)proxy).hello1();

    }
}
