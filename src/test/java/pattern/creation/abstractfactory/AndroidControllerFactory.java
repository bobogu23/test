package pattern.creation.abstractfactory;

/**
 * @author:ben.gu
 * @Date:2020/8/8 7:45 下午
 */
public class AndroidControllerFactory implements ControllerFactory {
    @Override
    public InterfaceController createInterfaceController() {
        return new AndroidInterfaceController();
    }

    @Override
    public OperationController createOperationController() {
        return new AndroidOperationController();
    }
}
