package com.csust.InternetCafe.business.controller;

import com.csust.InternetCafe.business.service.Appointment;
import com.csust.InternetCafe.business.service.Recharge;
import com.csust.InternetCafe.business.vo.Appointmentvo;
import com.csust.InternetCafe.common.commonconst.RedisOrSelect;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
    private RedisOrSelect redisOrSelect;



    @Resource
    private Appointment appointment;

    @PreAuthorize("hasAnyAuthority('read_index')")
    @GetMapping("/index")
    public String index(){
        logger.info("hello,has permission");
        return "index";
    }



    @PreAuthorize("hasAnyAuthority('appointment')")
    @RequestMapping(value = "/appointment,action")
    @PostMapping
    public String appointment(Authentication authentication ,@RequestBody Appointmentvo appointmentvo){
        String message = appointment.appointmentComputer(authentication.getName() , appointmentvo);
        logger.info(message);
        return "index";
    }

    @RequestMapping(value = "/personal.html")
    @GetMapping
    public String person(){
        return "personal";
    }

    @RequestMapping(value = "/recharge.html")
    @GetMapping
    public String recharge(){return "recharge";}
}
