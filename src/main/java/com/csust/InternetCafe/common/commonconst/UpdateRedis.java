package com.csust.InternetCafe.common.commonconst;

import com.csust.InternetCafe.business.service.Initialization;
import com.csust.InternetCafe.common.entity.Admin;
import com.csust.InternetCafe.common.entity.Computers;
import com.csust.InternetCafe.common.entity.Customers;
import com.csust.InternetCafe.common.service.RedisService;
import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @Author: 小凯神
 * @Date: 2019-04-19 14:00
 * @Description:
 */
@Service
public class UpdateRedis {
    private static Logger logger = LogManager.getLogger("HelloLog4j");

    @Resource
    private RedisOrSelect redisOrSelect;

    @Resource
    private RedisService redisService;

    @Resource
    private Initialization initialization;

    public void UpdateCustomers (Long uid , Customers customers){
        String value = new Gson().toJson(customers);
        redisService.set(Const.Redis_Customers+String.valueOf(uid) , value);
        logger.info("已将"+ customers.toString() +"的缓存更新");
    }

    public void UpdateAdmins (Long uid , Admin admin){
        String value = new Gson().toJson(admin);
        redisService.set(Const.Redis_Admin+String.valueOf(uid) , admin);
        logger.info("已将" + admin.toString() +"的缓存更新");
    }

    public void UpdateComputers(){
        initialization.LoadComputersToRedis();
    }

    public void UpdateComputers(int computerId , Computers computers) {
        Map<String, Object> map = redisService.hmget(Const.Redis_Computer);
        map.put(String .valueOf(computerId) , computers);
        redisService.hmset(Const.Redis_Computer,map);
        logger.info("已将" + map.size() +"机器的缓存更新");
    }
}
