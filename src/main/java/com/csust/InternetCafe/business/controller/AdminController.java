package com.csust.InternetCafe.business.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * @Author: 小凯神
 * @create: 2019-05-07 20:50
 * @Description:
 */

@Controller
public class AdminController {
    private static Logger logger = LogManager.getLogger("HelloLog4j");

    @RequestMapping("/admin.html")
    @PostMapping
    public String index(){
        return "admin";
    }

    @RequestMapping("/admin/temporary_appointment_list.html")
    @GetMapping
    public String temporary(){
        return "admin/temporary_appointment_list";
    }
}
