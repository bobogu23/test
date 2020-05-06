package pattern.struct;

/**
 * @author:ben.gu
 * @Date:2020/5/4 11:36 上午
 */
public class LinuxImpl implements ImageImpl {
    @Override
    public void doPaint(Matrix matrix) {
        System.out.println("在linux系统中绘制图像");
    }
}
