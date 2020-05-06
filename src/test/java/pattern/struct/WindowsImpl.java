package pattern.struct;

/**
 * @author:ben.gu
 * @Date:2020/5/4 11:35 上午
 */
public class WindowsImpl implements ImageImpl {
    @Override public void doPaint(Matrix matrix) {
        System.out.println("在windows系统中绘制图像");
    }
}
