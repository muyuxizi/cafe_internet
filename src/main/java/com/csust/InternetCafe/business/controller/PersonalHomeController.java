package com.csust.InternetCafe.business.controller;

import com.csust.InternetCafe.business.serviceImpl.PersonalHomeImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.net.Authenticator;

/**
 * @Author: 小凯神
 * @Date: 2019-04-15 17:03
 * @Description:
 */
@RestController
public class PersonalHomeController {

    @Resource
    private PersonalHomeImpl personalHome;

    private static Logger logger = LogManager.getLogger("HelloLog4j");

    @RequestMapping(value = "/index/information")
    @GetMapping
    public String getinformation(Authentication authentication){

            logger.info(authentication.getName());
            personalHome.getinformation(authentication.getName());
            return "nihao";

    }
}
