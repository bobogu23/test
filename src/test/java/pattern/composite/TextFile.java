package pattern.composite;

/**
 * @author:ben.gu
 * @Date:2020/8/15 10:43 下午
 */
public class TextFile {
    private String name;

    public TextFile(String name) {
        this.name = name;
    }

    public void print(){
        System.out.println("文本文件:"+name);
    }
}
