package com.csust.InternetCafe.business.service;

import com.csust.InternetCafe.common.config.RabbitmqConfig;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
/**
 * @Author: 小凯神
 * @Date: 2019-03-11 14:55
 * @Description:
 */
@Service
@RabbitListener(queues = RabbitmqConfig.QUEUE_A)
public class TestService {
    private static Logger logger = LogManager.getLogger("HelloLog4j");

    @RabbitHandler
    public void process(String context){
        logger.info("成功接受到信息" + context);
    }
}
