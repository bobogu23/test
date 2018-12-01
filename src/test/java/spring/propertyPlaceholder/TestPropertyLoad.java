package spring.propertyPlaceholder;

import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.PropertySources;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Map;

/**
 * @author:ben.gu
 * @Date:2018/11/29 上午11:52
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:PropertyPlaceHolder-ApplicationContext.xml"})
public class TestPropertyLoad implements ApplicationContextAware{
    @Autowired
    private  PropertiesTestBean bean;

    @Autowired
    private  AA aa;

    private ListableBeanFactory applicationContext;

    @Test
    public void test(){
        Map<String, PropertySourcesPlaceholderConfigurer> beansOfType = applicationContext
                .getBeansOfType(PropertySourcesPlaceholderConfigurer.class);


        System.out.println("appliedPropertySources-->"+ JSON.toJSONString(beansOfType));
        System.out.println("name-->"+ this.bean.toString());
        System.out.println("name-->"+ this.aa.toString());
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext=applicationContext;
    }
}
