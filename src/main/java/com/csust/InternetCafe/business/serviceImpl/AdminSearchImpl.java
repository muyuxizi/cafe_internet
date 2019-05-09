package com.csust.InternetCafe.business.serviceImpl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.csust.InternetCafe.business.service.AdminSearch;
import com.csust.InternetCafe.business.vo.TemporaryAppointmentvo;
import com.csust.InternetCafe.common.commonconst.RedisOrSelect;
import com.csust.InternetCafe.common.entity.Customers;
import com.csust.InternetCafe.common.entity.TemporaryAppointment;
import com.csust.InternetCafe.common.entity.Users;
import com.csust.InternetCafe.common.service.TemporaryAppointmentService;
import com.csust.InternetCafe.common.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: 小凯神
 * @create: 2019-05-09 10:44
 * @Description:
 */
@Service
public class AdminSearchImpl implements AdminSearch {

    @Resource
    private TemporaryAppointmentService temporaryAppointmentService;

    @Resource
    private UserService userService;

    @Resource
    private RedisOrSelect redisOrSelect;

    private static Logger logger = LogManager.getLogger("HelloLog4j");

    @Override
    public List<TemporaryAppointmentvo> temporaryAppointmetList() {
        List<TemporaryAppointmentvo> temporaryAppointmentvoList = new ArrayList<>();
        List<TemporaryAppointment> temporaryAppointmentList = new ArrayList<>();
        EntityWrapper<TemporaryAppointment> entityWrapper = new EntityWrapper<>();
        temporaryAppointmentList = temporaryAppointmentService.selectList(entityWrapper);
        logger.info(temporaryAppointmentList.size());
        for(TemporaryAppointment temporaryAppointment : temporaryAppointmentList){
            Users users = redisOrSelect.findUsers(temporaryAppointment.getUid());
            TemporaryAppointmentvo temporaryAppointmentvo = TemporaryAppointmentvo.builder()
                    .username(users.getUsername())
                    .amount(temporaryAppointment.getAmount())
                    .cafeName(temporaryAppointment.getCafeName())
                    .computerId(temporaryAppointment.getComputerId())
                    .startTime(temporaryAppointment.getAppointmentStartTime())
                    .endTime(temporaryAppointment.getAppointmentEndTime())
                    .build();
            temporaryAppointmentvoList.add(temporaryAppointmentvo);
        }
        return temporaryAppointmentvoList;
    }
}
