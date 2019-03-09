package spring.beanDefinition;

/**
 * @author:ben.gu
 * @Date:2018/12/28 12:01 AM
 */
public class CC {
    private BB bb;

    public CC() {
        System.err.println("create cc");
    }

    public void setBb(BB bb) {
        this.bb = bb;
        System.err.println("cc set bb ");
    }

    public BB getBb() {
        return bb;
    }

    public void say(){
        System.err.println("i am cc");
    }
}
