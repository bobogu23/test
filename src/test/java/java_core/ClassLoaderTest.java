package java_core;

import java_core.module.Foo;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;

/**
 * @author:ben.gu
 * @Date:2019/2/24 12:45 PM
 */
public class ClassLoaderTest {



    @Test
    public void testThreadContextClassLoader() {

        //        Thread.currentThread().setContextClassLoader();
    }

    /**
     * 实现java类的热替换
     */
    @Test
    public void testClassHotSwap() throws Exception {
        int i = 0 ;
        while (++i < 1) {
            invokeFooMethod();
            Thread.sleep(2000);
        }
    }

    private void invokeFooMethod()

            throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException,
            InvocationTargetException {
//        //同一类加载器只能加载一次同样的类，如果要再次加载只能创建新的类加载器来实现
        HotSwapClassLoader loader = new HotSwapClassLoader(this.getClass().getResource("/").getPath(),new String[]{"java_core.module.Foo"});
        Class<?> clazz = loader.loadClass("java_core.module.Foo");
        Object foo = clazz.newInstance();
        Method sayHello = foo.getClass().getMethod("sayHello", new Class[] {});
        sayHello.invoke(foo,new Object[]{});

//        System.err.println(""+Foo.class.getName());


    }

   static class HotSwapClassLoader extends ClassLoader {
        //加载指定目录下的class文件
        private String basedir;

        private HashSet<String> loadClassNames;

        public HotSwapClassLoader(String basedir, String[] classNames) {
            super(null);//
            this.basedir = basedir;
            loadClassNames = new HashSet<String>();
            loadClassBySelf(classNames);
        }

        private void loadClassBySelf(String[] classNames) {
            for (int i = 0; i < classNames.length; i++) {
                loadDirectly(classNames[i]);
                loadClassNames.add(classNames[i]);
            }

        }

        private Class loadDirectly(String className) {
            Class cls = null;
            StringBuffer sb = new StringBuffer(basedir);
            String classname = className.replace('.', File.separatorChar) + ".class";
            sb.append(File.separator +  classname);

            File classF = new File( sb.toString());
            try {
                cls = instantiateClass(className, new FileInputStream(classF), classF.length());
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            return cls;

        }

        private Class instantiateClass(String className, FileInputStream fileInputStream, long length) {
            byte[] raw = new byte[(int) length];
            try {
                fileInputStream.read(raw);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                fileInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return defineClass(className, raw, 0, raw.length);

        }

        @Override
        protected synchronized Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
            Class clazz = null;
            clazz = findLoadedClass(name);
            if (!this.loadClassNames.contains(name) && clazz == null) {
                //该类加载器没加载到class，交给系统类加载器加载
                clazz = getSystemClassLoader().loadClass(name);
                if (clazz == null) {
                    throw new ClassNotFoundException(name);
                }
            }
            if (resolve) {
                resolveClass(clazz);
            }
            return clazz;
        }
    }
}
