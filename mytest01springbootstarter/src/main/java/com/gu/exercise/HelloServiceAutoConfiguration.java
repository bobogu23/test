package com.gu.exercise;

/**
 * 自动配置类
 * @author:ben.gu
 * @Date:2019/12/19 7:30 PM
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(HelloServiceProperties.class)
@ConditionalOnClass(HelloServiceConfiguration.class)//当类路径下面有HelloServiceConfiguration此类时，自动配置
@ConditionalOnProperty(prefix = "com.gu.test", value = "enabled", matchIfMissing = true)
public class HelloServiceAutoConfiguration {
    @Autowired
    private HelloServiceProperties helloServiceProperties;

    /**
     * 当容器中没有指定bean时，创建此bean
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(HelloServiceConfiguration.class)
    public  HelloServiceConfiguration helloServiceConfiguration(){
        HelloServiceConfiguration configuration = new HelloServiceConfiguration();
        configuration.setName(helloServiceProperties.getName());
        configuration.setHobby(helloServiceProperties.getHobby());
        return configuration;
    }

}
