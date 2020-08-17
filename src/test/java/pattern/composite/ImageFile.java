package pattern.composite;

/**
 * @author:ben.gu
 * @Date:2020/8/15 10:41 下午
 */
public class ImageFile {
    private String name;

    public ImageFile(String name) {
        this.name = name;
    }

    public void print(){
        System.out.println("图像文件:"+name);
    }
}
