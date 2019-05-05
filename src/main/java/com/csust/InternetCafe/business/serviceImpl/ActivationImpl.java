package com.csust.InternetCafe.business.serviceImpl;

import com.csust.InternetCafe.business.service.Activation;
import com.csust.InternetCafe.common.commonconst.Const;
import com.csust.InternetCafe.common.commonconst.RedisOrSelect;
import com.csust.InternetCafe.common.commonconst.UpdateRedis;
import com.csust.InternetCafe.common.entity.Computers;
import com.csust.InternetCafe.common.entity.Customers;
import com.csust.InternetCafe.common.service.ComputersService;
import com.csust.InternetCafe.common.service.CustomersService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

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


    @Override
    @Transactional(rollbackFor = Exception.class)
    public String activation(String username, int computerid) {

        int uid = redisOrSelect.findUsers(username).getUid();

        Computers computers = redisOrSelect.findComputers(computerid);

        Customers customers = redisOrSelect.findCustomers(uid);


        //验证机器是否在使用
        if(computers.getIsUsed().equals(Const.Is_Used)){return "Sorry!该机器正在被使用";}


        // 用户占用未预约的机器
        if(computers.getIsUsed().equals(Const.Is_Not_Used) && customers.getIsUsed().equals(Const.Is_Not_Used) && computers.getIsAppointment().equals(Const.Is_Not_Appointment))
        {
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
            return "success!";
        }

        //用户占用已预约的机器


        return null;
    }
}
