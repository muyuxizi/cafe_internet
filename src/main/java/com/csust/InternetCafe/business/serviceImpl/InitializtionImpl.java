package com.csust.InternetCafe.business.serviceImpl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.csust.InternetCafe.business.service.Initialization;
import com.csust.InternetCafe.common.commonconst.Const;
import com.csust.InternetCafe.common.entity.Admin;
import com.csust.InternetCafe.common.entity.Computers;
import com.csust.InternetCafe.common.entity.Customers;
import com.csust.InternetCafe.common.entity.Users;
import com.csust.InternetCafe.common.service.AdminService;
import com.csust.InternetCafe.common.service.ComputersService;
import com.csust.InternetCafe.common.service.CustomersService;
import com.csust.InternetCafe.common.service.RedisService;
import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

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
    private ComputersService computersService;

    @Resource
    private AdminService adminService;

    private static Logger logger = LogManager.getLogger("HelloLog4j");

    @Override
    public void LoadToRedis(Long uid, int identity) {
        if(identity == Const.Customers_Identity){
            EntityWrapper<Customers> queryWrapper = new  EntityWrapper<>();
            queryWrapper.eq("uid" , uid);
            Customers customers = customersService.selectOne(queryWrapper);
            String value = new Gson().toJson(customers);
            redisService.set(Const.Redis_Customers+String.valueOf(uid) , value);
            logger.info("已将"+ customers.toString() +"加入到缓存");
        }
        if(identity == Const.Admin_Identity){
            EntityWrapper<Admin> queryWrapper = new  EntityWrapper<>();
            queryWrapper.eq(String.valueOf(uid) , uid);
            Admin admin = adminService.selectOne(queryWrapper);
            String value = new Gson().toJson(admin);
            redisService.set(Const.Redis_Admin+String.valueOf(uid) , admin);
            logger.info("已将" + admin.toString() +"加入到缓存");
        }
    }

    @Override
    public void LoadComputersToRedis() {
        Map<String , Object> hashMap = new HashMap<>();
        List<Computers> linkedList = new LinkedList<>();
        EntityWrapper<Computers> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("is_damaged", Const.Computer_Is_Normal);
        linkedList = computersService.selectList(entityWrapper);
        for (Computers computers : linkedList){
            hashMap.put(String.valueOf(computers.getComputerId()) , computers);
        }
        //hashMap = linkedList.stream().collect(Collectors.toMap(Computers::getComputerId , computers -> computers));
        redisService.hmset(Const.Redis_Computer,hashMap);
        logger.info("已经将"+linkedList.size()+"加入到了redis中");
    }
}
