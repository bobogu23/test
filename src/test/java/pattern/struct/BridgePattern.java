package pattern.struct;

/**
 * 结构行模式--桥接模式
 *
 * @author:ben.gu
 * @Date:2020/5/4 10:55 上午
 */
public class BridgePattern {

    public static void main(String[] args) {
        Image image =new JPGImage();
        ImageImpl imageImpl = new LinuxImpl();
        image.setImageimpl(imageImpl);
        image.parseFile("哈哈");
    }
}
