package pattern.creation.abstractfactory;

/**
 * @author:ben.gu
 * @Date:2020/8/8 7:42 下午
 */
public interface ControllerFactory {
    InterfaceController createInterfaceController();

    OperationController createOperationController();
}
