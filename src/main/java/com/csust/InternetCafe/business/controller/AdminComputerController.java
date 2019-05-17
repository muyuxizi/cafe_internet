package com.csust.InternetCafe.business.controller;

import com.csust.InternetCafe.business.service.ComputerToGrid;
import com.csust.InternetCafe.common.commonconst.RedisOrSelect;
import com.csust.InternetCafe.common.entity.Computers;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author: 小凯神
 * @create: 2019-05-11 15:09
 * @Description:
 */
@RestController
public class AdminComputerController {

    @Resource
    private RedisOrSelect redisOrSelect;

    @Resource
    private ComputerToGrid computerToGrid;

    private static Logger logger = LogManager.getLogger("HelloLog4j");

    @RequestMapping(value = "/computer_list.action")
    @GetMapping
    public List<Computers> getComputers(){
        List<Computers> computersList = new ArrayList<>();
        computersList = computerToGrid.get();
        return computersList;
    }

    @RequestMapping(value = "/computer_one.action")
    public List<Computers> getOneComputers(@RequestParam Map<String, Object> params){
        String sid =(String) params.get("id");
        Computers computers = computerToGrid.getone(Integer.valueOf(sid));
        List<Computers> computersList = new ArrayList<>();
        computersList.add(computers);
        return computersList;
    }

    @RequestMapping(value = "/computer_list/add.action")
    @PostMapping
    public String add(Authentication authentication , @RequestBody Computers computers){
      String result = computerToGrid.add(authentication.getName() ,computers);
        if("success".equals(result)){
            return "success";
        }
        return "error";
    }

    @RequestMapping(value = "/computer_list/update.action")
    @PostMapping
    public String update(Authentication authentication , @RequestBody Computers computers){
        String result = computerToGrid.update(authentication.getName(),computers);
        if("success".equals(result)){
            return "success";
        }
        return "error";
    }

    @RequestMapping(value = "/computer_list/del.action")
    @PostMapping
    public String del(Authentication authentication , @RequestBody Computers computers){
        String result = computerToGrid.del(authentication.getName() , computers.getComputerId());
        if("success".equals(result)){
            return "success";
        }
        return "error";
    }
}
