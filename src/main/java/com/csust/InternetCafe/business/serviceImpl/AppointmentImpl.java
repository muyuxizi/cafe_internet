package com.csust.InternetCafe.business.serviceImpl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.csust.InternetCafe.business.service.Appointment;
import com.csust.InternetCafe.business.vo.Appointmentvo;
import com.csust.InternetCafe.common.commonconst.Const;
import com.csust.InternetCafe.common.commonconst.RedisOrSelect;
import com.csust.InternetCafe.common.commonconst.UpdateRedis;
import com.csust.InternetCafe.common.entity.Computers;
import com.csust.InternetCafe.common.entity.Customers;
import com.csust.InternetCafe.common.entity.TemporaryAppointment;
import com.csust.InternetCafe.common.service.ComputersService;
import com.csust.InternetCafe.common.service.CustomersService;
import com.csust.InternetCafe.common.service.TemporaryAppointmentService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author: 小凯神
 * @Date: 2019-04-18 14:57
 * @Description:
 */
@Service
public class AppointmentImpl implements Appointment {

    @Resource
    private CustomersService customersService;

    @Resource
    private ComputersService computersService;

    @Resource
    private TemporaryAppointmentService temporaryAppointmentService;

    @Resource
    private RedisOrSelect redisOrSelect;

    @Resource
    private UpdateRedis updateRedis;

    private static Logger logger = LogManager.getLogger("HelloLog4j");

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String appointmentComputer(String username , Appointmentvo appointmentvo) {

        int uid = redisOrSelect.findUsers(username).getUid();

        StringBuffer stringBuffer = new StringBuffer("");

        Computers computers = redisOrSelect.findComputers(appointmentvo.getComputerId());

        Customers customers = redisOrSelect.findCustomers(uid);

        //验证两个数据是否合理
        if(appointmentvo.getStartTime()>23 || appointmentvo.getStartTime() <8 || appointmentvo.getEndTime() >23 || appointmentvo.getEndTime() < 9)
        {return  "预约时间错误";}

        //预定时间必须大于等于三小时
        if(appointmentvo.getEndTime()-appointmentvo.getStartTime()<=2){return  "预定时间必须大于等于三小时";}

        //如果该用户正在上网，预约不成立
        if(customers.getIsUsed().equals(Const.Is_Used)){return  "该用户正在上网，无法预约"; }

        //如果该用户已经预约了，预约不成立
        if(customers.getIsAppointment().equals(Const.Is_Appointment)){return "该用户已经预约！";}

        //如果该计算机正在使用，无法预约
        if(computers.getIsUsed().equals(Const.Is_Used)){return  "该计算机正在被使用，无法预约";}

        //如果余额不足，那么无法预约
        if(customers.getAccountMoney()<(appointmentvo.getEndTime()-appointmentvo.getStartTime()+1)*computers.getLevel()){
            return  "对不起，您的余额不足，请及时充值!";
        }

        //如果该机跟用户都没有预约，成功预约直接跳到插入语句


        //如果该机已经有用户预定了,判断是否冲突
        if(computers.getIsAppointment().equals(Const.Is_Appointment)){
            List<TemporaryAppointment> temporaryAppointmentList = new ArrayList<>();
            EntityWrapper<TemporaryAppointment> entityWrapper = new EntityWrapper<>();
            entityWrapper.eq("computer_id" , appointmentvo.getComputerId());
            temporaryAppointmentList = temporaryAppointmentService.selectList(entityWrapper);
            for (TemporaryAppointment temporaryAppointment : temporaryAppointmentList){
                if ((appointmentvo.getStartTime() >= temporaryAppointment.getAppointmentEndTime()+1 )||
                        (appointmentvo.getEndTime()+1 <= temporaryAppointment.getAppointmentStartTime())
                        ){}else {
                    return "对不起，该时间段有人预约了";
                }
            }
        }


        //向数据库和缓存更新数据
        TemporaryAppointment temporaryAppointment = TemporaryAppointment.builder()
                .appointmentStartTime(appointmentvo.getStartTime())
                .appointmentEndTime(appointmentvo.getEndTime())
                .amount((appointmentvo.getEndTime() - appointmentvo.getStartTime()+1)*computers.getLevel())
                .cafeName(computers.getCafeName())
                .uid(uid)
                .computerId(appointmentvo.getComputerId())
                .id(0)
                .build();

        customers.setIsAppointment(Const.Is_Appointment);
        if(computers.getIsAppointment().equals(Const.Is_Not_Appointment)) {
            computers.setIsAppointment(Const.Is_Appointment);
        }

        try {
            temporaryAppointmentService.insert(temporaryAppointment);
            customersService.updateById(customers);
            computersService.updateById(computers);
        }catch (Exception e){
            logger.error(e.getMessage()+"");
            throw e;
        }

        updateRedis.UpdateCustomers(uid , customers);
        updateRedis.UpdateComputers(appointmentvo.getComputerId() , computers);

        return "success";
    }
}
