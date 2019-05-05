package com.csust.InternetCafe.common.service;

import org.redisson.Redisson;
import org.redisson.api.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author: 小凯神
 * @Date: 2019-04-23 10:27
 * @Description:
 */
@Service
public class RedissionService {

    @Resource
    private RedissonClient redissonClient;

}
