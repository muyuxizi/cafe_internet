package com.csust.InternetCafe.business.schedule;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.csust.InternetCafe.business.service.Activation;
import com.csust.InternetCafe.common.commonconst.Const;
import com.csust.InternetCafe.common.commonconst.RedisOrSelect;
import com.csust.InternetCafe.common.entity.*;
import com.csust.InternetCafe.common.service.CustomersService;
import com.csust.InternetCafe.common.service.SurfInternetRecordsService;
import com.csust.InternetCafe.common.service.TemporaryAppointmentService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @Author: 小凯神
 * @create: 2019-05-16 19:29
 * @Description:
 */
@Service
public class AccountCheck {

    @Resource
    private CustomersService customersService;

    @Resource
    private TemporaryAppointmentService temporaryAppointmentService;

    @Resource
    private SurfInternetRecordsService surfInternetRecordsService;

    @Resource
    private RedisOrSelect redisOrSelect;

    @Resource
    private Activation activation;

    private static Logger logger = LogManager.getLogger("HelloLog4j");

    @Scheduled(cron = "0 */1 * * * ?")
    private void accountCheck(){
        logger.info("自动检测余额");
        List<Customers> customersList = new ArrayList<>();
        EntityWrapper<Customers> customersEntityWrapper = new EntityWrapper<>();
        customersEntityWrapper.eq("is_used",Const.Is_Used);
        customersList = customersService.selectList(customersEntityWrapper);
        for(Customers customers : customersList){
            EntityWrapper<SurfInternetRecords> surfInternetRecordsEntityWrapper = new EntityWrapper<>();
            List<SurfInternetRecords> surfInternetRecordsList = new ArrayList<>();
            surfInternetRecordsEntityWrapper.eq("uid" , customers.getUid());
            surfInternetRecordsList = surfInternetRecordsService.selectList(surfInternetRecordsEntityWrapper);
            for(SurfInternetRecords surfInternetRecords : surfInternetRecordsList){
                if(surfInternetRecords.getStartTime().equals(surfInternetRecords.getEndTime())){
                    long minute = System.currentTimeMillis()/60000 - surfInternetRecords.getStartTime()/60000;
                    Integer number = (int)( minute/30 + 1);
                    Computers computers = redisOrSelect.findComputers(surfInternetRecords.getComputerId());
                    int money = number*computers.getLevel()/2;
                    if((customers.getAccountMoney() - money)<0){
                        Users users = redisOrSelect.findUsers(customers.getUid());
                        activation.activation(users.getUsername() , surfInternetRecords.getComputerId());
                        logger.info(computers.getComputerId() + "号机已自动下机");
                    }
                }
            }
        }
    }

    @Scheduled(cron = "0 0 * * * ?")
    public void appointmentCheck(){
        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        List<TemporaryAppointment> temporaryAppointmentList = new ArrayList<>();
        EntityWrapper<TemporaryAppointment> temporaryAppointmentEntityWrapper = new EntityWrapper<>();
        temporaryAppointmentEntityWrapper.eq("appointment_start_time" , hour);
        for(TemporaryAppointment temporaryAppointment : temporaryAppointmentList){
            Customers customers = redisOrSelect.findCustomers(temporaryAppointment.getUid());
            Users users = redisOrSelect.findUsers(customers.getUid());
            if(customers.getIsUsed().equals(Const.Is_Not_Used)){
                activation.activation(users.getUsername() , temporaryAppointment.getComputerId());
            }
        }
    }

}
