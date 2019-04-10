package com.csust.InternetCafe.business.controller;


import com.csust.InternetCafe.common.config.RabbitmqConfig;
import com.csust.InternetCafe.common.entity.EsSurfInternetRecords;
import com.csust.InternetCafe.common.mapper.EsSurfInternetRecordsRepository;
import com.csust.InternetCafe.common.pojo.MsgProducer;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.ArrayList;

/**
 * @Author: 小凯神
 * @Date: 2019-02-28 10:18
 * @Description:
 */

@Controller
public class TestController {

    @Resource
    private MsgProducer msgProducer;

    @Resource
    private EsSurfInternetRecordsRepository esSurfInternetRecordsRepository;
    private static Logger logger = LogManager.getLogger("HelloLog4j");

    @RequestMapping("/login.html")
    @PostMapping
    public String login() {
        EsSurfInternetRecords esSurfInternetRecords = EsSurfInternetRecords.builder()
                .id(5)
                .uid(10)
                .computerId(13)
                .consumptionAmount(10)
                .startTime(5000)
                .endTime(10000)
                .cafeName("lengjiang")
                .build();
        esSurfInternetRecordsRepository.save(esSurfInternetRecords);
        msgProducer.sendMsg("这是第一条消息");
        logger.info("第一条消息已经发送");
        return "login.html";
    }

    @RequestMapping("/error.html")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        logger.info("密码错误？");
        return "login.html";
    }

    @RequestMapping("/login/timeout")
    @GetMapping
    public String timeOut(){
        return "login.html";
    }
}
