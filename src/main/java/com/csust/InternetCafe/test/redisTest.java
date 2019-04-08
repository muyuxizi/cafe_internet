package com.csust.InternetCafe.test;


import com.csust.InternetCafe.common.entity.Users;
import com.csust.InternetCafe.common.service.RedisService;
import com.csust.InternetCafe.common.service.UserService;
import com.sun.glass.ui.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @Author: 小凯神
 * @Date: 2019-02-26 15:50
 * @Description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class redisTest {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Resource
    private RedisService redisService;

    @Resource
    private UserService userService;

    @Test
    public void test(){
        /*Users users = userService.selectById(1);
       redisService.set("first" , users);
        logger.info(String.valueOf(redisService.get("first")));*/
      /*  Users.builder()
                .id(null)
                .username("test")
                .uid(1)
                .password(passwordEncoder().encode("123456"))*/
    }
}
