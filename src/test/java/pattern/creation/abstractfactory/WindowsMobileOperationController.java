package pattern.creation.abstractfactory;

/**
 * @author:ben.gu
 * @Date:2020/8/8 7:47 下午
 */
public class WindowsMobileOperationController implements OperationController {

    @Override
    public void control() {
        System.err.println("WindowsMobileOperation");

    }
}
