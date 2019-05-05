package com.csust.InternetCafe.business.ajax;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.csust.InternetCafe.common.commonconst.RedisOrSelect;
import com.csust.InternetCafe.common.entity.Computers;
import com.csust.InternetCafe.common.entity.TemporaryAppointment;
import com.csust.InternetCafe.common.service.AppointmentService;
import com.csust.InternetCafe.common.service.RedisService;
import com.csust.InternetCafe.common.service.TemporaryAppointmentService;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Author: 小凯神
 * @Date: 2019-04-21 11:54
 * @Description:
 */


public class AppointmentAjaxImpl {

    @Resource
    private RedissonClient redissonClient;

    @Resource
    private AppointmentService appointmentService;

    @Resource
    private TemporaryAppointmentService temporaryAppointmentService;

    @Resource
    private RedisOrSelect redisOrSelect;

    public List<Integer> findseats(int start , int end , String cafeName , int isSmoking){
        List<Integer>  list = new ArrayList<>();
        Map<String , Object> map = new HashMap<>();
        map = redisOrSelect.findComputers();

        //筛选出map中符合规定的机器
        for(Object object : map.values()){
            Computers computers = (Computers)object;
            if(computers.getIsSomking() == isSmoking){
                list.add(computers.getComputerId());
            }
        }

        //删除时间有冲突的机器
        List<TemporaryAppointment> temporaryAppointmentList = new ArrayList<>();
        EntityWrapper<TemporaryAppointment> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("cafe_name" , cafeName);
        temporaryAppointmentList = temporaryAppointmentService.selectList(entityWrapper);
        for(TemporaryAppointment temporaryAppointment : temporaryAppointmentList){
            if ((start >= temporaryAppointment.getAppointmentEndTime()+1 )||
                    (end+1 <= temporaryAppointment.getAppointmentStartTime())
                    ){}else {
                list.remove(temporaryAppointment.getComputerId());
            }
        }
        return list;
    }


 }
