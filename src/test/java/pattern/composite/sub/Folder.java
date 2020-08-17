package pattern.composite.sub;

import java.util.ArrayList;
import java.util.List;

/**
 * @author:ben.gu
 * @Date:2020/8/16 2:51 下午
 */
public class Folder extends Component {
    private String name;

    private List<Component> childs = new ArrayList<>();

    public Folder(String name) {
        this.name = name;
    }

    @Override
    public void print() {
        System.out.println("文件夹：" + name);
        for (Component c : childs) {
            c.print();
        }

    }

    @Override
    public void add(Component c) {
        childs.add(c);
    }

    @Override
    public void remove(Component c) {
        childs.remove(c);
    }

    @Override
    public Component getChild(int i) {
        return childs.get(i);
    }
}
