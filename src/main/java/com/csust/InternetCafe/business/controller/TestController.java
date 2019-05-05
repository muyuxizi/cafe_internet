package com.csust.InternetCafe.business.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @Author: 小凯神
 * @Date: 2019-04-23 18:57
 * @Description:
 */
@RestController
public class TestController {
    @Resource
    private RedissonClient redissonClient;

    private static Logger logger = LogManager.getLogger("HelloLog4j");


    @RequestMapping(value = "/test/redission")
    @GetMapping
    public String testredisssion(){
        RLock rLock = redissonClient.getFairLock("redissiontest" );

        try {
            boolean res = rLock.tryLock(5, 10 , TimeUnit.SECONDS);
            if(res)
            {logger.info("加锁成功");}
            else
            {
                logger.info("加锁失败");
                return "error!";
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "success";
    }
}
