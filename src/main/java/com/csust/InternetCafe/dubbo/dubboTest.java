package com.csust.InternetCafe.dubbo;

import com.alibaba.dubbo.config.annotation.Service;
import com.csust.InternetCafe.Interface.dubbotest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

/**
 * @Author: 小凯神
 * @Date: 2019-04-10 10:22
 * @Description:
 */
@Component
@Service(version = "1.0.0" , timeout = 10000 , interfaceClass = dubbotest.class)
public class dubboTest implements dubbotest {
    private static Logger logger = LogManager.getLogger("HelloLog4j");
    @Override
    public String say(String name){
        return "Hello";
    }
}
