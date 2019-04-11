package com.csust.InternetCafe.common.pojo;

import com.csust.InternetCafe.common.config.RabbitmqConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * @Author: 小凯神
 * @Date: 2019-03-11 10:37
 * @Description:
 */
@Component
public class MsgProducer implements RabbitTemplate.ConfirmCallback {

    private static Logger logger = LogManager.getLogger("HelloLog4j");

    private RabbitTemplate rabbitTemplate;

    @Autowired
    public MsgProducer(RabbitTemplate rabbitTemplate){
        this.rabbitTemplate = rabbitTemplate;
        rabbitTemplate.setConfirmCallback(this);
    }

    public void sendMsg(String content){
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        rabbitTemplate.convertAndSend(RabbitmqConfig.EXCHANGE_A, RabbitmqConfig.ROUTINGKEY_A, content, correlationData);
    }

    public void sendMsgB(String content){
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        rabbitTemplate.convertAndSend(RabbitmqConfig.EXCHANGE_B, RabbitmqConfig.ROUTINGKEY_B, content,correlationData);
    }

    public void sendMsgC(String content){
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        rabbitTemplate.convertAndSend(RabbitmqConfig.EXCHANGE_C, RabbitmqConfig.ROUTINGKEY_C, content,correlationData);
    }


    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String s) {
        logger.info("回调" + correlationData);
        if (ack) {
            logger.info("消息成功被消费");
        } else {
          logger.info("消息消费失败");
        }
    }
}
