package com.csust.InternetCafe.business.serviceImpl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.csust.InternetCafe.business.service.PersonalHome;
import com.csust.InternetCafe.business.vo.PersonalHomevo;
import com.csust.InternetCafe.common.entity.Customers;
import com.csust.InternetCafe.common.entity.Users;
import com.csust.InternetCafe.common.service.RedisService;
import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.Type;

/**
 * @Author: 小凯神
 * @Date: 2019-04-15 17:28
 * @Description:
 */
@Service
public class PersonalHomeImpl implements PersonalHome {

    @Resource
    private RedisService redisService;

    private static Logger logger = LogManager.getLogger("HelloLog4j");

    @Override
    public PersonalHomevo getinformation(String username) {

        String jsonString = String.valueOf(redisService.get(username));
        Users users = new Gson().fromJson(jsonString , Users.class);
        logger.info(users);
        if(users == null){
            logger.info("Redis读取缓存失败");
        }
        return null;
    }
}
