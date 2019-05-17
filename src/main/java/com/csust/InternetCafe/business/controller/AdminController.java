package com.csust.InternetCafe.business.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PreAuthorize("hasAnyAuthority('read_admin')")
    @RequestMapping("/admin.html")
    @GetMapping
    public String index(){
        return "admin";
    }


    @RequestMapping("/admin/temporary_appointment_list.html")
    @GetMapping
    public String temporary(){
        return "admin/temporary_appointment_list";
    }

    @RequestMapping("/admin/appointment_list.html")
    @GetMapping
    public String appointment(){
        return "admin/appointment_list";
    }

    @RequestMapping("/admin/computer_list.html")
    @GetMapping
    public String computers(){
        return "admin/computer_list";
    }

    @RequestMapping("/admin/surf_record_list.html")
    @GetMapping
    public String surfInternetRecords(){
        return "admin/surf_record_list";
    }

    @RequestMapping("/admin/customers_list.html")
    @GetMapping
    public String customersLists(){
        return "admin/customers_list";
    }


}
