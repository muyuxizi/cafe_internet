package com.csust.InternetCafe.dubbo;

import com.alibaba.dubbo.config.annotation.Service;
import com.csust.InternetCafe.business.service.Appointment;
import com.csust.InternetCafe.business.service.Recharge;
import com.csust.InternetCafe.business.vo.Appointmentvo;
import com.csust.InternetCafe.common.service.AppointmentService;
import com.csust.InternetCafe.dubbo.dubboInterface.appointmentdubbo;
import com.csust.InternetCafe.dubbo.dubboInterface.dubbotest;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Author: 小凯神
 * @Date: 2019-04-22 15:10
 * @Description:
 */
@Component
@Service(version = "1.0.0" , timeout = 10000 , interfaceClass = appointmentdubbo.class)
public class appointmentDubbo implements appointmentdubbo {

    @Resource
    private Appointment appointment ;

    @Resource
    private Recharge recharge;

    @Override
    public String appointment(String username, Appointmentvo appointmentvo) {
        String message = appointment.appointmentComputer(username , appointmentvo);
        return message;
    }

    @Override
    public String recharge(String username, int money) {
        String message = recharge.recharge(username , money);
        return message;
    }
}
