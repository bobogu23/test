package netty.simplerpcframework.service.impl;

import netty.simplerpcframework.service.HelloWordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author:ben.gu
 * @Date:2020/1/7 10:02 PM
 */
@Service
public class HelloWordServiceImpl implements HelloWordService {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public String hello(String msg) {
        logger.info("msg:{}", msg);

        return msg + "_" + System.currentTimeMillis();
    }
}
