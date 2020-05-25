package groovy;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;

/**
 * @author:ben.gu
 * @Date:2020/5/8 7:24 下午
 */
public class GroovyTest {

    public static void main(String[] args) {
        // *) groovy 代码
        String script = "println 'hello'; 'name = ' + name;";

        // *) 传入参数
        Binding binding = new Binding();
        binding.setVariable("name", "lilei");

        // *) 执行脚本代码
        GroovyShell shell = new GroovyShell(binding);
        Object res = shell.evaluate(script);
        System.out.println(res);
    }
}
