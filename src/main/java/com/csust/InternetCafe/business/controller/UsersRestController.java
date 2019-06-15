package com.csust.InternetCafe.business.controller;

import com.csust.InternetCafe.business.ajax.AppointmentAjaxImpl;
import com.csust.InternetCafe.business.service.*;
import com.csust.InternetCafe.business.vo.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    @Resource
    private Recharge recharge;

    @Resource
    private Exchange exchange;


    @Resource
    private Appointment appointment;

    @Resource
    private AppointmentAjaxImpl appointmentAjax;

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

    @PreAuthorize("hasAnyAuthority('activation')")
    @RequestMapping(value = "/activation.action")
    @PostMapping
    public String activationOrSettlement(Authentication authentication , @RequestBody Activationvo activationvo){
        String result = activation.activation(authentication.getName() , activationvo.getComputerId());
        logger.info(result);
        return result;
    }

    @RequestMapping(value = "/exchange.action")
    @PostMapping
    public String exchange(Authentication authentication , @RequestBody Exchangevo exchangevo){
        String result = exchange.exchange(authentication.getName() , exchangevo.getOldcomputer() , exchangevo.getNewcomputer());
        logger.info(result);
        return result;
    }

    @PreAuthorize("hasAnyAuthority('recharge')")
    @RequestMapping(value = "/pay.action")
    @PostMapping
    public String recharge(Authentication authentication , @RequestBody Rechargevo rechargevo){
        String result = recharge.recharge(authentication.getName() , rechargevo.getMoney());
        logger.info(result);
        return result;
    }

    @RequestMapping(value = "/loadsetts")
    @PostMapping
    public List<Integer> get(@RequestBody Choicevo choicevo){
        List<Integer> list = new ArrayList<>();
        list = appointmentAjax.findseats(choicevo.getStart() , choicevo.getEnd() , "wangyu" , choicevo.getSomking());
        return list;
    }

    @RequestMapping(value = "/judgeTime")
    @PostMapping
    public String juege(@RequestBody Choicevo choicevo){
        String result ="";
        result = appointmentAjax.judgeTime(choicevo.getStart() , choicevo.getEnd());
        return result;
    }

    @PreAuthorize("hasAnyAuthority('appointment')")
    @RequestMapping(value = "/appointment,action")
    @PostMapping
    public String appointment(Authentication authentication ,@RequestBody Appointmentvo appointmentvo){
        String message = appointment.appointmentComputer(authentication.getName() , appointmentvo);
        logger.info(message);
        return message;
    }
}
