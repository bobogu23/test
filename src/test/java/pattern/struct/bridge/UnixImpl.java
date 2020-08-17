package pattern.struct.bridge;

/**
 * @author:ben.gu
 * @Date:2020/5/4 11:36 上午
 */
public class UnixImpl implements ImageImpl {
    @Override
    public void doPaint(Matrix matrix) {
        System.out.println("在unix系统中绘制图像");

    }
}
