package com.shop.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author:ben.gu
 * @Date:2020/3/10 9:08 PM
 */
@Controller
@RequestMapping("/demo")
public class DemoController {

    @ResponseBody
    @RequestMapping("/hello")
    public String hello(String name) {
        System.err.println("name:"+name);

        return "hello";
    }
}
