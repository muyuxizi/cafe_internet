package com.csust.InternetCafe.business.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.csust.InternetCafe.business.service.AdminManage;
import com.csust.InternetCafe.common.entity.Admin;
import com.csust.InternetCafe.common.entity.Computers;
import com.csust.InternetCafe.common.entity.Users;
import com.csust.InternetCafe.common.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author: 小凯神
 * @create: 2019-05-18 19:22
 * @Description:
 */
@RestController
public class SuperAdminadminController {

    @Resource
    private AdminManage adminManage;

    @Resource
    private UserService userService;

    @RequestMapping(value = "/admin_list.action")
    @GetMapping
    public List<Admin> getComputers(){
       List<Admin> adminList = new ArrayList<>();
       adminList = adminManage.get();
        return adminList;
    }

    @RequestMapping(value = "/admin_one.action")
    public List<Admin> getOne(@RequestParam Map<String, Object> params){
        String sid =(String) params.get("name");
        Admin admin = adminManage.getone(sid);
        List<Admin> adminList = new ArrayList<>();
        adminList.add(admin);
        return adminList;
    }

    @RequestMapping(value = "/admin_list/add.action")
    @PostMapping
    public String add( @RequestBody Admin admin){
        String result =adminManage.add(admin);
        if("success".equals(result)){
            return "success";
        }
        return "error";
    }

    @RequestMapping(value = "/admin_list/update.action")
    @PostMapping
    public String update( @RequestBody Admin admin){
        String result = adminManage.update(admin);
        if("success".equals(result)){
            return "success";
        }
        return "error";
    }

    @RequestMapping(value = "/admin_list/del.action")
    @PostMapping
    public String del( @RequestBody Admin admin){
        String result = adminManage.del(admin.getUid() , admin.getId());
        if("success".equals(result)){
            return "success";
        }
        return "error";
    }
}
