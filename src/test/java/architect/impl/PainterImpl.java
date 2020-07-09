package architect.impl;

import architect.Component;
import architect.Painter;

/**
 * @author:ben.gu
 * @Date:2020/6/24 10:47 下午
 */
public class PainterImpl implements Painter {

    @Override
    public void paint(Component component) {
        System.err
                .println(String.format("print %s (%s)", component.getClass().getSimpleName(), component.getName()));
    }
}
