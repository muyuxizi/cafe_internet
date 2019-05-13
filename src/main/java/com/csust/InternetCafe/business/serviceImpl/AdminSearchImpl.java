package com.csust.InternetCafe.business.serviceImpl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.csust.InternetCafe.business.service.AdminSearch;
import com.csust.InternetCafe.business.vo.Appointmentoutvo;
import com.csust.InternetCafe.business.vo.Surfrecordvo;
import com.csust.InternetCafe.business.vo.TemporaryAppointmentvo;
import com.csust.InternetCafe.common.commonconst.RedisOrSelect;
import com.csust.InternetCafe.common.entity.*;
import com.csust.InternetCafe.common.service.AppointmentService;
import com.csust.InternetCafe.common.service.SurfInternetRecordsService;
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
    private AppointmentService appointmentService;

    @Resource
    private UserService userService;

    @Resource
    private RedisOrSelect redisOrSelect;

    @Resource
    private SurfInternetRecordsService surfInternetRecordsService;

    private static Logger logger = LogManager.getLogger("HelloLog4j");

    @Override
    public List<TemporaryAppointmentvo> temporaryAppointmetList() {
        List<TemporaryAppointmentvo> temporaryAppointmentvoList = new ArrayList<>();
        List<TemporaryAppointment> temporaryAppointmentList = new ArrayList<>();
        EntityWrapper<TemporaryAppointment> entityWrapper = new EntityWrapper<>();
        temporaryAppointmentList = temporaryAppointmentService.selectList(entityWrapper);
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

    @Override
    public List<Appointmentoutvo> appointmentvooutlist() {
        List<Appointmentoutvo> appointmentoutvos = new ArrayList<>();
        List<Appointment> appointments = new ArrayList<>();
        EntityWrapper<Appointment> entityWrapper = new EntityWrapper<>();
        appointments = appointmentService.selectList(entityWrapper);
        for(Appointment appointment : appointments){
            Users users = redisOrSelect.findUsers(appointment.getUid());
            Appointmentoutvo appointmentoutvo = Appointmentoutvo.builder()
                    .username(users.getUsername())
                    .amount(appointment.getAmount())
                    .appointmentDate(appointment.getAppointmentDate())
                    .appointmentTimeSlot(appointment.getAppointmentTimeSlot())
                    .cafeName(appointment.getCafeName())
                    .computerId(appointment.getComputerId())
                    .build();
            appointmentoutvos.add(appointmentoutvo);
        }
        return appointmentoutvos;
    }

    @Override
    public List<Surfrecordvo> surfInternetRecordlist() {
        List<Surfrecordvo> surfrecordvos = new ArrayList<>();
        List<SurfInternetRecords> surfInternetRecordsList = new ArrayList<>();
        EntityWrapper<SurfInternetRecords> entityWrapper = new EntityWrapper<>();
        surfInternetRecordsList = surfInternetRecordsService.selectList(entityWrapper);
        for(SurfInternetRecords surfInternetRecords : surfInternetRecordsList){
            Users users = redisOrSelect.findUsers(surfInternetRecords.getUid());
            Surfrecordvo surfrecordvo = Surfrecordvo.builder()
                    .username(users.getUsername())
                    .cafeName(surfInternetRecords.getCafeName())
                    .computerId(surfInternetRecords.getComputerId())
                    .consumptionAmount(surfInternetRecords.getConsumptionAmount())
                    .startTime(surfInternetRecords.getStartTime())
                    .endTime(surfInternetRecords.getEndTime())
                    .build();
           surfrecordvos.add(surfrecordvo);
        }
        return surfrecordvos;
    }
}
