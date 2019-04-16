package com.csust.InternetCafe.business.controller;

import com.csust.InternetCafe.business.service.Recharge;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @Author: 小凯神
 * @Date: 2019-02-28 18:38
 * @Description:
 */
@Controller
public class IndexController {
    private static Logger logger = LogManager.getLogger("HelloLog4j");

    @Resource
    private Recharge recharge;

    @PreAuthorize("hasAnyAuthority('read_index')")
    @GetMapping("/index")
    public String index(){
        logger.info("hello,has permission");
        return "index";
    }


    @PreAuthorize("hasAnyAuthority('recharge')")
    @RequestMapping(value = "/pay.action")
    @PostMapping
    public String recharge(Authentication authentication , HttpServletRequest request){
        String message = recharge.recharge(authentication.getName() , Integer.valueOf(request.getParameter("money")));
        logger.info(message);
        return "index";
    }

}
