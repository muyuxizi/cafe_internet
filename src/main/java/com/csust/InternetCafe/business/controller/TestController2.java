package com.csust.InternetCafe.business.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Author: 小凯神
 * @Date: 2019-02-28 18:38
 * @Description:
 */
@Controller
public class TestController2 {
    private static Logger logger = LogManager.getLogger("HelloLog4j");

    @PreAuthorize("hasAnyAuthority('write')")
    @GetMapping("/index")
    public String index(){
        logger.info("hello,has permission");
        return "hello";
    }
}
