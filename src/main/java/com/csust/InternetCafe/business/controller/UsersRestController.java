package com.csust.InternetCafe.business.controller;

import com.csust.InternetCafe.business.service.Activation;
import com.csust.InternetCafe.business.service.LoadAndRegister;
import com.csust.InternetCafe.business.vo.Registervo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: 小凯神
 * @create: 2019-05-09 19:22
 * @Description:
 */

@RestController
public class UsersRestController {

    @Resource
    private LoadAndRegister loadAndRegister;

    @Resource
    private Activation activation;

    private static Logger logger = LogManager.getLogger("HelloLog4j");

    @RequestMapping(value = "/register.html")
    @PostMapping
    public String register(@RequestBody Registervo registervo , HttpServletResponse httpServletResponse){
        String result = loadAndRegister.Register(registervo);
        if("success".equals(result)){
            return "success";
        }
        logger.info(registervo);
        logger.info(result);
        return result;
    }

    @RequestMapping(value = "/activation.action")
    @PostMapping
    public String activationOrSettlement(Authentication authentication , int computerId){
        String result = activation.activation(authentication.getName() , computerId);
        logger.info(result);
        return result;
    }
}
