package architect.impl;

import architect.Component;

/**
 * @author:ben.gu
 * @Date:2020/6/24 11:52 下午
 */
public class LinkLable implements Component {
    private String name;

    public LinkLable(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }
}
