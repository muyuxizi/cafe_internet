package com.csust.InternetCafe.business.ajax;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.csust.InternetCafe.common.commonconst.RedisOrSelect;
import com.csust.InternetCafe.common.entity.TemporaryAppointment;
import com.csust.InternetCafe.common.service.AppointmentService;
import com.csust.InternetCafe.common.service.RedisService;
import com.csust.InternetCafe.common.service.TemporaryAppointmentService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: 小凯神
 * @Date: 2019-04-21 11:54
 * @Description:
 */


public class AppointmentAjaxImpl {

    @Resource
    private AppointmentService appointmentService;

    @Resource
    private TemporaryAppointmentService temporaryAppointmentService;

    @Resource
    private RedisOrSelect redisOrSelect;

    public List<Integer> findseats(int start , int end , String cafeName){
        Map<String , Object> map = new HashMap<>();
        map = redisOrSelect.findComputers();
        List<Integer>  list = new ArrayList<>();
        List<TemporaryAppointment> temporaryAppointmentList = new ArrayList<>();
        EntityWrapper<TemporaryAppointment> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("cafe_name" , cafeName);
        temporaryAppointmentList = temporaryAppointmentService.selectList(entityWrapper);
        for(TemporaryAppointment temporaryAppointment : temporaryAppointmentList){

        }
        return list;
    }
 }
