package com.csust.InternetCafe.business.controller;


import com.csust.InternetCafe.business.service.LoadAndRegister;
import com.csust.InternetCafe.business.vo.Registervo;
import com.csust.InternetCafe.common.config.RabbitmqConfig;
import com.csust.InternetCafe.common.entity.EsSurfInternetRecords;
import com.csust.InternetCafe.common.mapper.EsSurfInternetRecordsRepository;
import com.csust.InternetCafe.common.pojo.MsgProducer;
import net.minidev.json.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

/**
 * @Author: 小凯神
 * @Date: 2019-02-28 10:18
 * @Description:
 */

@Controller
public class LoadController {

    @Resource
    private MsgProducer msgProducer;

    @Resource
    private LoadAndRegister loadAndRegister;

    @Resource
    private EsSurfInternetRecordsRepository esSurfInternetRecordsRepository;
    private static Logger logger = LogManager.getLogger("HelloLog4j");

    @RequestMapping("/login.html")
    @PostMapping
    public String login() {
        EsSurfInternetRecords esSurfInternetRecords = EsSurfInternetRecords.builder()
                .id(6)
                .uid(10)
                .computerId(13)
                .consumptionAmount(10)
                .startTime(Long.valueOf(5000))
                .endTime(Long.valueOf(10000))
                .cafeName("changsha")
                .build();
       // esSurfInternetRecordsRepository.save(esSurfInternetRecords);
        msgProducer.sendMsg("登陆请求");
        logger.info("登陆请求已经发送");
        return "login";
    }

    @RequestMapping("/error.html")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        logger.info("密码错误？");
        return "login";
    }

    @RequestMapping("/login/timeout")
    @GetMapping
    public String timeOut(){
        return "login";
    }


    @RequestMapping("/index.html")
    @GetMapping
    public String yanzheng(){
        logger.info("加载基础配置");
        return "index";
    }


   @RequestMapping(value = "/register.action")
    @PostMapping
    public String toRegister(){
        return "register";
    }




}
