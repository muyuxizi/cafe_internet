package com.csust.InternetCafe.business.ajax;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.csust.InternetCafe.common.commonconst.Const;
import com.csust.InternetCafe.common.commonconst.RedisOrSelect;
import com.csust.InternetCafe.common.entity.Computers;
import com.csust.InternetCafe.common.entity.TemporaryAppointment;
import com.csust.InternetCafe.common.service.AppointmentService;
import com.csust.InternetCafe.common.service.RedisService;
import com.csust.InternetCafe.common.service.TemporaryAppointmentService;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @Author: 小凯神
 * @Date: 2019-04-21 11:54
 * @Description:
 */

@Service
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
            if(computers.getIsSomking() == isSmoking && computers.getIsUsed().equals(Const.Is_Not_Used)){
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

        Collections.sort(list);
        return list;
    }

    public String judgeTime(int start , int end){

        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        if(start <= hour) {return "预约开始时间不正确";}
        if(start >= end){return  "预约时间不正确";}
        return "";
    }

 }
