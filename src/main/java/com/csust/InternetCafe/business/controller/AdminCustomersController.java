package com.csust.InternetCafe.business.controller;

import com.csust.InternetCafe.business.service.PersonalHome;
import com.csust.InternetCafe.business.vo.PersonalHomevo;
import com.csust.InternetCafe.common.entity.Computers;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author: 小凯神
 * @create: 2019-05-14 13:49
 * @Description:
 */
@RestController
public class AdminCustomersController {

    @Resource
    private PersonalHome personalHome;

    @RequestMapping(value = "/customers_list.action")
    @GetMapping
    public List<PersonalHomevo> getall(){
        List<PersonalHomevo> personalHomevoList = new ArrayList<>();
        personalHomevoList = personalHome.getcustomerslist();
        return personalHomevoList;
    }

    @RequestMapping(value = "/customers_list/update.action")
    @PostMapping
    public String update(Authentication authentication , @RequestBody PersonalHomevo personalHomevo){
        String result = personalHome.update(authentication.getName() ,personalHomevo);
        if("success".equals(result)){
            return "success";
        }
        return "error";
    }

    @RequestMapping(value = "/customers_list/getone.action")
    public List<PersonalHomevo> getone(@RequestParam Map<String, Object> params){
        String username =(String) params.get("username");
        List<PersonalHomevo> personalHomevoList = new ArrayList<>();
        PersonalHomevo personalHomevo = personalHome.getinformation(username);
        personalHomevoList.add(personalHomevo);
        return personalHomevoList;
    }
}
