package com.csust.InternetCafe.business.serviceImpl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.csust.InternetCafe.business.service.Activation;
import com.csust.InternetCafe.common.commonconst.Const;
import com.csust.InternetCafe.common.commonconst.RedisOrSelect;
import com.csust.InternetCafe.common.commonconst.UpdateRedis;
import com.csust.InternetCafe.common.entity.Computers;
import com.csust.InternetCafe.common.entity.Customers;
import com.csust.InternetCafe.common.entity.SurfInternetRecords;
import com.csust.InternetCafe.common.entity.TemporaryAppointment;
import com.csust.InternetCafe.common.service.ComputersService;
import com.csust.InternetCafe.common.service.CustomersService;
import com.csust.InternetCafe.common.service.SurfInternetRecordsService;
import com.csust.InternetCafe.common.service.TemporaryAppointmentService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @Author: 小凯神
 * @Date: 2019-04-25 14:37
 * @Description:
 */
@Service
public class ActivationImpl implements Activation {

    private static Logger logger = LogManager.getLogger("HelloLog4j");

    @Resource
    private RedisOrSelect redisOrSelect;

    @Resource
    private UpdateRedis updateRedis;

    @Resource
    private ComputersService computersService;

    @Resource
    private CustomersService customersService;

    @Resource
    private TemporaryAppointmentService temporaryAppointmentService;

    @Resource
    private SurfInternetRecordsService surfInternetRecordsService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String activation(String username, int computerid) {

        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);

        int uid = redisOrSelect.findUsers(username).getUid();

        Computers computers = redisOrSelect.findComputers(computerid);

        Customers customers = redisOrSelect.findCustomers(uid);

        String message = "";



        //验证机器是否在使用
        if(computers.getIsUsed().equals(Const.Is_Used)){return "Sorry!该机器正在被使用";}

        //判断用户当前是否预约
        if(customers.getIsUsed().equals(Const.Is_Not_Used) && customers.getIsAppointment().equals(Const.Is_Appointment)){
            TemporaryAppointment temporaryAppointment = new TemporaryAppointment();
            EntityWrapper<TemporaryAppointment> entityWrapper = new EntityWrapper<>();
            entityWrapper.eq("uid" , customers.getUid());
            temporaryAppointment = temporaryAppointmentService.selectOne(entityWrapper);
            if(hour >= temporaryAppointment.getAppointmentStartTime() && hour <= temporaryAppointment.getAppointmentEndTime() ){
                if(computerid == temporaryAppointment.getComputerId()){

                    computers.setIsAppointment(Const.Is_Not_Appointment);
                    customers.setIsAppointment(Const.Is_Not_Appointment);
                   insertToSurf(computers , customers , uid ,computerid);

                    return "success";
                } else {
                    return "Sorry! , 请到已预约的机器"+computerid+"号上网！";
                }
            }
            if(hour < temporaryAppointment.getAppointmentStartTime()){
                message = "success! 请注意，"+temporaryAppointment.getAppointmentStartTime()+"点请换到"+computerid+"号机上网";
            }
        }


        // 用户占用未预约的机器
        if(customers.getIsUsed().equals(Const.Is_Not_Used) && computers.getIsAppointment().equals(Const.Is_Not_Appointment))
        {
            insertToSurf(computers , customers , uid ,computerid);
            return "success!" + message;
        }

        //用户占用已预约的机器
        if(customers.getIsUsed().equals(Const.Is_Not_Used) && computers.getIsAppointment().equals(Const.Is_Appointment))
        {
            int distance = 16;
            List<TemporaryAppointment> temporaryAppointmentList = new ArrayList<>();
            EntityWrapper<TemporaryAppointment> entityWrapper = new EntityWrapper<>();
            entityWrapper.eq("computer_id" , computerid);
            temporaryAppointmentList = temporaryAppointmentService.selectList(entityWrapper);
            for (TemporaryAppointment temporaryAppointment : temporaryAppointmentList){
                if(hour >= temporaryAppointment.getAppointmentStartTime() && hour <= temporaryAppointment.getAppointmentEndTime()){
                    return "Sorry,目前机器已经有人预约";
                }
                if(hour < temporaryAppointment.getAppointmentStartTime()){
                        if((temporaryAppointment.getAppointmentStartTime() - hour) <= distance){
                            distance = temporaryAppointment.getAppointmentStartTime() - hour;
                        }
                }
            }
            if(distance != 16){
                insertToSurf(computers , customers , uid ,computerid);
                return "success,请注意，该机器" +  (hour+distance) + "点有用户预约！" + message;
            }else {
                insertToSurf(computers , customers , uid ,computerid);
                return "success" + message;
            }
        }

        //用户换未预约的机器
        //用户换预约了的机器


        //用户结账下机
        if(customers.getIsUsed().equals(Const.Is_Used)){
            SurfInternetRecords surfInternetRecords = new SurfInternetRecords();
            EntityWrapper<SurfInternetRecords> entityWrapper = new EntityWrapper<>();
            entityWrapper.eq("computer_id" , computerid)
            .eq("amount",0)
            .eq("uid",uid);
            surfInternetRecords = surfInternetRecordsService.selectOne(entityWrapper);
            long minute = System.currentTimeMillis()/60000 - surfInternetRecords.getStartTime()/60000;
            Integer number = (int)( minute/30 + 1);

            customers.setIsUsed(Const.Is_Not_Used);
            computers.setIsUsed(Const.Is_Not_Used);
            surfInternetRecords.setEndTime(System.currentTimeMillis());
            surfInternetRecords.setConsumptionAmount(number*computers.getLevel()/2);
            try {
                customersService.updateById(customers);
                computersService.updateById(computers);
                surfInternetRecordsService.updateById(surfInternetRecords);
            }catch (Exception e){
                logger.error(e.getMessage() + "");
                throw e;
            }
            updateRedis.UpdateCustomers(uid, customers);
            updateRedis.UpdateComputers(computerid , computers);
        }

        return "";
    }


    @Transactional(rollbackFor = Exception.class)
    public void insertToSurf(Computers computers , Customers customers , int uid, int computerid ){
        customers.setIsUsed(Const.Is_Used);
        computers.setIsUsed(Const.Is_Used);
        try {
            customersService.updateById(customers);
            computersService.updateById(computers);
        }catch (Exception e){
            logger.error(e.getMessage() + "");
            throw e;
        }
        updateRedis.UpdateCustomers(uid, customers);
        updateRedis.UpdateComputers(computerid , computers);
        SurfInternetRecords surfInternetRecords = SurfInternetRecords.builder()
                .cafeName(computers.getCafeName())
                .computerId(computerid)
                .consumptionAmount(0)
                .endTime(System.currentTimeMillis())
                .startTime(System.currentTimeMillis())
                .uid(uid)
                .build();
        try {
            surfInternetRecordsService.insert(surfInternetRecords);
        }catch (Exception e){
            logger.error(e.getMessage()+"");
            throw e;
        }
    }


}
