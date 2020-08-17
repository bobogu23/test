package pattern.struct.bridge;

/**
 * @author:ben.gu
 * @Date:2020/5/4 11:44 上午
 */
public class GIFImage extends Image {
    @Override
    public void parseFile(String fileName) {
        Matrix m = new Matrix();
        imageimpl.doPaint(m);
        System.out.println(fileName + ",格式为GIF");
    }
}
