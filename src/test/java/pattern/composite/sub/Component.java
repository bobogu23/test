package pattern.composite.sub;

/**
 * @author:ben.gu
 * @Date:2020/8/16 2:37 下午
 */
public abstract class Component {
    public abstract void print();

    public abstract void add(Component c);

    public abstract void remove(Component c);

    public abstract Component getChild(int i);
}
