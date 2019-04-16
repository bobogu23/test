package spring.aop.schemaaop;
import org.aspectj.lang.JoinPoint;

/**
 * 注意 JoinPoint 的包名org.aspectj.lang ,否则会报错:error at ::0 formal unbound in pointcut
 * @author:ben.gu
 * @Date:2019/4/8 11:44 PM
 */
public class SchemaBaseAop {

    public void doBefore(JoinPoint j){
        System.err.println("doBefore");
    }

    public void doAfterReturning(JoinPoint jp ,Object retValue){
        System.err.println("doAfterReturning"+",return value:"+retValue);
    }
}
