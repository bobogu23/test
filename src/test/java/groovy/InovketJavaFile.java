package groovy;

import groovy.lang.GroovyClassLoader;

/**
 * @author:ben.gu
 * @Date:2020/5/8 7:31 下午
 */
public class InovketJavaFile {

    public static void main(String[] args) {
        ClassLoader cl = new InovketJavaFile().getClass().getClassLoader();
        GroovyClassLoader groovyCl = new GroovyClassLoader(cl);
        try {
            //从文件中读取，将实现IFoo接口的groovy类写在一个groovy文件中
            //Class groovyClass = groovyCl.parseClass(new File("./src/sample/Foo.groovy"));
            //直接使用Groovy字符串,也可以获得正确结果
            Class groovyClass = groovyCl.parseClass("class Foo implements groovy.IFoo {public Object run(Object foo) {return 2+2>1;}}");//这个返回true
            IFoo foo = (IFoo) groovyClass.newInstance();
            System.out.println(foo.run(new Integer(2)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
