package pattern.composite.sub;

/**
 * @author:ben.gu
 * @Date:2020/8/16 2:44 下午
 */
public class TextFile extends Component {
    private String name ;

    public TextFile(String name) {
        this.name = name;
    }

    @Override public void print() {
        System.out.println("文件："+name);
    }

    @Override public void add(Component c) {
        System.err.println("不支持该操作");
    }

    @Override public void remove(Component c) {
        System.err.println("不支持该操作");
    }

    @Override public Component getChild(int i) {
        System.err.println("不支持该操作");
        return null;
    }
}
