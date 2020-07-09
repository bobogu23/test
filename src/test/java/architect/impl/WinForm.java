package architect.impl;

import architect.Component;
import architect.Painter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author:ben.gu
 * @Date:2020/6/24 10:53 下午
 */
public class WinForm implements Component {
    private List<Component> childs = new ArrayList<>();

    private String name;

    public WinForm(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }

    public void addChild(Component component) {
        childs.add(component);
    }

    @Override
    public void print(Painter painter) {
        painter.paint(this);
        childs.forEach(c -> c.print(painter));
    }
}
