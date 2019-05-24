package com.csust.InternetCafe.business.controller;

import com.csust.InternetCafe.business.service.Activation;
import com.csust.InternetCafe.business.service.Exchange;
import com.csust.InternetCafe.business.service.LoadAndRegister;
import com.csust.InternetCafe.business.service.Recharge;
import com.csust.InternetCafe.business.vo.Activationvo;
import com.csust.InternetCafe.business.vo.Exchangevo;
import com.csust.InternetCafe.business.vo.Rechargevo;
import com.csust.InternetCafe.business.vo.Registervo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
}
