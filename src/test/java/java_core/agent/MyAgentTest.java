package java_core.agent;

/**
 * @author:ben.gu
 * @Date:2019/4/26 6:43 PM
 */
public class MyAgentTest {

    public static void main(String args[]){
        System.err.println("hahahha");
        MyAgentTest test =new MyAgentTest();
        test.test();

        try {
            Thread.sleep((long) (Math.random()*10));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void test(){
        System.err.println("testtesttesttest");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
