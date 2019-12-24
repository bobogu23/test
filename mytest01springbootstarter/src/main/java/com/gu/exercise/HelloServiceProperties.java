package com.gu.exercise;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * properties 配置类
 * @author:ben.gu
 * @Date:2019/12/19 7:27 PM
 */
@ConfigurationProperties(prefix = "com.gu.test")
public class HelloServiceProperties {

    private String name ="xx";
    private String hobby ="swim";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }
}
