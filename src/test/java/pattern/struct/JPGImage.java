package pattern.struct;

/**
 * @author:ben.gu
 * @Date:2020/5/4 11:41 上午
 */
public class JPGImage extends Image {

    @Override
    public void parseFile(String fileName) {
        Matrix m = new Matrix();
        imageimpl.doPaint(m);
        System.out.println(fileName + ",格式为JPG");
    }
}
