package com.csust.InternetCafe.business.serviceImpl;

import com.csust.InternetCafe.business.service.Appointment;
import com.csust.InternetCafe.business.vo.Appointmentvo;
import com.csust.InternetCafe.common.commonconst.RedisOrSelect;
import com.csust.InternetCafe.common.entity.Computers;
import com.csust.InternetCafe.common.entity.Customers;
import com.csust.InternetCafe.common.service.TemporaryAppointmentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author: 小凯神
 * @Date: 2019-04-18 14:57
 * @Description:
 */
@Service
public class AppointmentImpl implements Appointment {

    @Resource
    private TemporaryAppointmentService temporaryAppointmentService;

    @Resource
    private RedisOrSelect redisOrSelect;

    @Override
    public String appointmentComputer(Integer uid , Appointmentvo appointmentvo) {

        Computers computers = redisOrSelect.findComputers(appointmentvo.getComputerId());

        Customers customers = redisOrSelect.findCustomers(uid);


        return null;
    }
}
