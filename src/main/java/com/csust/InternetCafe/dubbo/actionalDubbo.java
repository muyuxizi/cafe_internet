package com.csust.InternetCafe.dubbo;

import com.alibaba.dubbo.config.annotation.Service;
import com.csust.InternetCafe.business.service.Activation;
import com.csust.InternetCafe.dubbo.dubboInterface.actionaldubbo;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Author: 小凯神
 * @create: 2019-06-09 20:15
 * @Description:
 */
@Component
@Service(version = "1.0.0" , timeout = 10000 , interfaceClass = actionaldubbo.class)
public class actionalDubbo implements actionaldubbo{
    @Resource
    private Activation activation;

    @Override
    public String login(String username , Integer computerId){
        String message = activation.activation(username , computerId);
        return message;
    }
}
