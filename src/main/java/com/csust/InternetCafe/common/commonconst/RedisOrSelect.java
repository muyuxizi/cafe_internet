package com.csust.InternetCafe.common.commonconst;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.csust.InternetCafe.common.entity.Admin;
import com.csust.InternetCafe.common.entity.Customers;
import com.csust.InternetCafe.common.entity.Users;
import com.csust.InternetCafe.common.service.AdminService;
import com.csust.InternetCafe.common.service.CustomersService;
import com.csust.InternetCafe.common.service.RedisService;
import com.csust.InternetCafe.common.service.UserService;
import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author: 小凯神
 * @Date: 2019-04-15 18:38
 * @Description:
 */
@Service
public class RedisOrSelect {

    @Resource
    private RedisService redisService;

    @Resource
    private UserService userService;

    @Resource
    private CustomersService customersService;

    @Resource
    private AdminService adminService;

    private static Logger logger = LogManager.getLogger("HelloLog4j");

    public Users findUsers(String username){
        Users users = null;
        String jsonString = String.valueOf(redisService.get(username));
        users = new Gson().fromJson(jsonString , Users.class);
        if(users == null){
            //如果Redis中不存在，那么从数据库中获取并且写入Redis
            EntityWrapper<Users> newWrapper = new  EntityWrapper<>();
            newWrapper.eq("username" , username);
            users = userService.selectOne(newWrapper);
            String value = new Gson().toJson(users);
            redisService.set(username , value);
            logger.info("已将"+ users.toString() + "加入到Redis");
        }
        return  users;
    }

    public Customers findCustomers(int uid){
        Customers customers = null;
        String jsonString = String.valueOf(redisService.get(String.valueOf(uid)));
        customers = new Gson().fromJson(jsonString , Customers.class);
        if(customers == null){
            //如果Redis中不存在，那么从数据库中获取并且写入Redis
            EntityWrapper<Customers> newWrapper = new  EntityWrapper<>();
            newWrapper.eq("uid" , uid);
            customers = customersService.selectOne(newWrapper);
            String value = new Gson().toJson(customers);
            redisService.set(String.valueOf(uid) , value);
            logger.info("已将"+ customers.toString() + "加入到Redis");
        }
        return  customers;
    }

    public Admin findadmins(int uid){
        Admin admin = null;
        String jsonString = String.valueOf(redisService.get(String.valueOf(uid)));
        admin = new Gson().fromJson(jsonString , Admin.class);
        if(admin == null){
            //如果Redis中不存在，那么从数据库中获取并且写入Redis
            EntityWrapper<Admin> newWrapper = new  EntityWrapper<>();
            newWrapper.eq("uid" , uid);
            admin = adminService.selectOne(newWrapper);
            String value = new Gson().toJson(admin);
            redisService.set(String.valueOf(uid) , value);
            logger.info("已将"+ admin.toString() + "加入到Redis");
        }
        return  admin;
    }


}