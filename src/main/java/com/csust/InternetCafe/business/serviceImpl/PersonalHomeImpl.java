package com.csust.InternetCafe.business.serviceImpl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.csust.InternetCafe.business.service.PersonalHome;
import com.csust.InternetCafe.business.vo.PersonalHomevo;
import com.csust.InternetCafe.common.commonconst.RedisOrSelect;
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

    @Resource
    private RedisOrSelect redisOrSelect;

    private static Logger logger = LogManager.getLogger("HelloLog4j");

    @Override
    public PersonalHomevo getinformation(String username) {
        Users users = redisOrSelect.findUsers(username);
        int uid = users.getUid();


        return null;
    }
}
