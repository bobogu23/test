package spring.aop.invocationhandler;

import org.junit.Test;
import sun.misc.ProxyGenerator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.lang.reflect.Proxy;

/**
 * jdk dynamic proxy
 * @author:ben.gu
 * @Date:2019/3/13 12:06 AM
 */
public class InvocationHandlerTest {

    @Test
    public void test() throws Exception {
        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");

        IRequestable instance = (IRequestable) Proxy.newProxyInstance(getClass().getClassLoader(), new Class[] { IRequestable.class },
                new RequestCtrInvocationHandler(new IRequestableImpl()));
        instance.request();

        byte[]classFile = ProxyGenerator.generateProxyClass("Proxy5",IRequestableImpl.class.getInterfaces());

        File file =new File(getClass().getClassLoader().getResource("").getPath()+"/Proxy5.class");

        FileOutputStream fos =new FileOutputStream(file);

        fos.write(classFile);

        fos.flush();

        fos.close();

    }
}
