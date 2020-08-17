package pattern.struct.bridge;

/**
 * 抽象图类
 * @author:ben.gu
 * @Date:2020/5/4 11:32 上午
 */
public abstract class Image {
    protected ImageImpl imageimpl;

    public void setImageimpl(ImageImpl imageimpl) {
        this.imageimpl = imageimpl;
    }

    public abstract void parseFile(String fileName);
}
