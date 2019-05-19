package com.csust.InternetCafe.business.schedule;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.csust.InternetCafe.common.entity.Appointment;
import com.csust.InternetCafe.common.entity.EverydayBill;
import com.csust.InternetCafe.common.entity.TemporaryAppointment;
import com.csust.InternetCafe.common.service.AppointmentService;
import com.csust.InternetCafe.common.service.EveryBillService;
import com.csust.InternetCafe.common.service.TemporaryAppointmentService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @Author: 小凯神
 * @create: 2019-05-19 15:26
 * @Description:
 */
@Service
public class ScheduleInInitialization {

    private static Logger logger = LogManager.getLogger("HelloLog4j");

    @Resource
    private AppointmentService appointmentService;

    @Resource
    private TemporaryAppointmentService temporaryAppointmentService;

    @Resource
    private EveryBillService everydayBillService;

    @Scheduled(cron = "0 0 0 * * ?")
    @Transactional(rollbackFor = Exception.class)
    public void initialization(){
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int date = c.get(Calendar.DATE);
        String time = year + "-" + month + "-" +date;

        EverydayBill everydayBill = EverydayBill.builder()
                .id(0)
                .saleProfit(0)
                .surfInternetProfit(0)
                .time(time)
                .build();
        try {
            everydayBillService.insert(everydayBill);
        }catch (Exception e){
            throw  e;
        }


        List<TemporaryAppointment> temporaryAppointmentList = new ArrayList<>();
        EntityWrapper<TemporaryAppointment> entityWrapper = new EntityWrapper<>();
        temporaryAppointmentList = temporaryAppointmentService.selectList(entityWrapper);
        List<Appointment> appointmentList = new ArrayList<>();
        for(TemporaryAppointment temporaryAppointment : temporaryAppointmentList){
            Appointment appointment = Appointment.builder()
                    .appointmentDate(time)
                    .appointmentTimeSlot(temporaryAppointment.getAppointmentStartTime() + "-" + temporaryAppointment.getAppointmentEndTime())
                    .amount(temporaryAppointment.getAmount())
                    .cafeName(temporaryAppointment.getCafeName())
                    .computerId(temporaryAppointment.getComputerId())
                    .id(0)
                    .uid(temporaryAppointment.getUid())
                    .build();
            appointmentList.add(appointment);
        }
        try {
            temporaryAppointmentService.delete(entityWrapper);
            appointmentService.insertBatch(appointmentList);
        }catch (Exception e){
            throw e;
        }
    }
}
