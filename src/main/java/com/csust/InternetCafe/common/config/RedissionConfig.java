package com.csust.InternetCafe.common.config;

import io.lettuce.core.RedisClient;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.redisson.config.Config;

import javax.annotation.Resource;

/**
 * @Author: 小凯神
 * @Date: 2019-04-23 18:46
 * @Description:
 */
@Configuration
public class RedissionConfig {

    @Resource
    private RedisProperties redisProperties;


    @Bean
    public RedissonClient redissonClient(){
        Config config = new Config();
        String redisUrl = String.format("redis://%s:%s",redisProperties.getHost()+"",redisProperties.getPort()+"");
        config.useSingleServer().setAddress(redisUrl).setPassword(redisProperties.getPassword());
        config.useSingleServer().setDatabase(3);
        return Redisson.create(config);
    }
}
