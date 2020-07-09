package architect.impl;

import architect.Component;
import architect.Painter;

/**
 * @author:ben.gu
 * @Date:2020/6/24 10:54 下午
 */
public class Picture implements Component {
    private String name;

    public Picture(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }

}
