package pattern.creation.abstractfactory;

/**
 * @author:ben.gu
 * @Date:2020/8/8 7:45 下午
 */
public class SymbianControllerFactory implements ControllerFactory {
    @Override
    public InterfaceController createInterfaceController() {
        return new SymbianInterfaceController();
    }

    @Override
    public OperationController createOperationController() {
        return new SymbianOperationController();
    }
}
