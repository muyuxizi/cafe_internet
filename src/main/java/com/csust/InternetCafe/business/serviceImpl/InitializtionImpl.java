package com.csust.InternetCafe.business.serviceImpl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.csust.InternetCafe.business.service.Initialization;
import com.csust.InternetCafe.common.entity.Admin;
import com.csust.InternetCafe.common.entity.Customers;
import com.csust.InternetCafe.common.entity.Users;
import com.csust.InternetCafe.common.service.AdminService;
import com.csust.InternetCafe.common.service.CustomersService;
import com.csust.InternetCafe.common.service.RedisService;
import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author: 小凯神
 * @Date: 2019-04-15 17:35
 * @Description:
 */
@Service
public class InitializtionImpl implements Initialization {

    @Resource
    private RedisService redisService;

    @Resource
    private CustomersService customersService;

    @Resource
    private AdminService adminService;

    private static Logger logger = LogManager.getLogger("HelloLog4j");

    @Override
    public void LoadToRedis(int uid, int identity) {
        if(identity == 1){
            EntityWrapper<Customers> queryWrapper = new  EntityWrapper<>();
            queryWrapper.eq("uid" , uid);
            Customers customers = customersService.selectOne(queryWrapper);
            String value = new Gson().toJson(customers);
            redisService.set("uid" , value);
            logger.info("已将"+ customers.toString() +"加入到缓存");
        }
        if(identity == 2){
            EntityWrapper<Admin> queryWrapper = new  EntityWrapper<>();
            queryWrapper.eq("uid" , uid);
            Admin admin = adminService.selectOne(queryWrapper);
            String value = new Gson().toJson(admin);
            redisService.set("uid" , admin);
            logger.info("已将" + admin.toString() +"加入到缓存");
        }
    }
}
