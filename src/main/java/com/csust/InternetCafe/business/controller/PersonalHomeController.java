package com.csust.InternetCafe.business.controller;

import com.csust.InternetCafe.business.service.LoadAndRegister;
import com.csust.InternetCafe.business.serviceImpl.PersonalHomeImpl;
import com.csust.InternetCafe.business.vo.PersonalHomevo;
import com.csust.InternetCafe.business.vo.Registervo;
import net.minidev.json.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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

    @Resource
    private LoadAndRegister loadAndRegister;


    private static Logger logger = LogManager.getLogger("HelloLog4j");

    @PreAuthorize("hasAnyAuthority('customers_read')")
    @RequestMapping(value = "/index/information")
    @GetMapping
    public PersonalHomevo getinformation(Authentication authentication){

            logger.info(authentication.getName());
            PersonalHomevo personalHomevo = personalHome.getinformation(authentication.getName());
            return personalHomevo;
    }


}
