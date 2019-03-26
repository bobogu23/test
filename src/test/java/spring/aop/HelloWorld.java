package spring.aop;

/**
 * @author:ben.gu
 * @Date:2019/3/12 11:04 PM
 */
public class HelloWorld {
    String msg;

    public HelloWorld(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void  helloMsg(){
        System.err.println(msg);
    }
}
