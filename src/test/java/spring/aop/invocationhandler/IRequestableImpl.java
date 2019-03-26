package spring.aop.invocationhandler;

/**
 * @author:ben.gu
 * @Date:2019/3/13 12:03 AM
 */
public class IRequestableImpl implements IRequestable {
    @Override public void request() {
        System.err.println("haha int IRequestableImpl");
    }
}
