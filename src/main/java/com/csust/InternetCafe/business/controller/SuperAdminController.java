package com.csust.InternetCafe.business.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author: 小凯神
 * @create: 2019-05-17 12:13
 * @Description:
 */
@Controller
public class SuperAdminController {

    private static Logger logger = LogManager.getLogger("HelloLog4j");

    @RequestMapping("/superadmin.html")
    @GetMapping
    public String spueradmin(){
        return "superadmin";
    }

    @RequestMapping("/superadmin/admin_list.html")
    @GetMapping
    public String adminList(){
        return "superadmin/admin_list";
    }

    @RequestMapping("/superadmin/everyday_bill.html")
    @GetMapping
    public String everyday_BillList(){
        return "superadmin/everyday_bill";
    }

    @RequestMapping("/superadmin/operation_records.html")
    @GetMapping
    public String operation_recordsList(){
        return "superadmin/operation_records";
    }

    @RequestMapping("/superadmin/permission_list.html")
    @GetMapping
    public String permissionList(){
        return "superadmin/permission_list";
    }

}
