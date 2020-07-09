package architect.impl;

import architect.Component;
import architect.Painter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author:ben.gu
 * @Date:2020/6/24 11:52 下午
 */
public class Frame implements Component {
    private String name;

    private List<Component> childs = new ArrayList<>();

    public Frame(String name) {
        this.name = name;
    }

    public void addChild(Component component) {
        childs.add(component);
    }

    @Override
    public void print(Painter painter) {
        painter.paint(this);
        childs.forEach(painter::paint);
    }

    @Override
    public String getName() {
        return this.name;
    }
}
