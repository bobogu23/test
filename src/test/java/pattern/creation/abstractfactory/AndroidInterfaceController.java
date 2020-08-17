package pattern.creation.abstractfactory;

/**
 * @author:ben.gu
 * @Date:2020/8/8 7:46 下午
 */
public class AndroidInterfaceController implements InterfaceController {

    @Override public void control() {
        System.err.println("AndroidInterface");
    }
}
