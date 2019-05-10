package com.csust.InternetCafe.business.serviceImpl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.csust.InternetCafe.business.service.Activation;
import com.csust.InternetCafe.business.service.Exchange;
import com.csust.InternetCafe.common.commonconst.Const;
import com.csust.InternetCafe.common.commonconst.RedisOrSelect;
import com.csust.InternetCafe.common.commonconst.UpdateRedis;
import com.csust.InternetCafe.common.entity.Computers;
import com.csust.InternetCafe.common.entity.Customers;
import com.csust.InternetCafe.common.entity.SurfInternetRecords;
import com.csust.InternetCafe.common.service.ComputersService;
import com.csust.InternetCafe.common.service.CustomersService;
import com.csust.InternetCafe.common.service.SurfInternetRecordsService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

public class ExchangeImpl implements Exchange {

    private static Logger logger = LogManager.getLogger("HelloLog4j");

    @Resource
    private RedisOrSelect redisOrSelect;

    @Resource
    private Activation activation;

    @Resource
    private UpdateRedis updateRedis;

    @Resource
    private ComputersService computersService;

    @Resource
    private CustomersService customersService;

    @Resource
    private SurfInternetRecordsService surfInternetRecordsService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String exchange(String username, int oldComputer, int newComputer) {


        int uid = redisOrSelect.findUsers(username).getUid();

        Customers customers = redisOrSelect.findCustomers(uid);

        Computers oldcomputer = redisOrSelect.findComputers(oldComputer);

        Computers newcomputer = redisOrSelect.findComputers(newComputer);

        String message = "";

        //判断输入的新旧机器是否合理
        if(oldcomputer.getIsUsed().equals(Const.Is_Not_Used) || newcomputer.getIsUsed().equals(Const.Is_Used) || oldComputer == newComputer || customers.getIsUsed().equals(Const.Is_Not_Used)){
            return "请输入的正确机器号";
        }

        //结束本次上网，开始新的上网
        String result = activation.activation(username , oldComputer);
        if(result.contains("success")){
            message = activation.activation(username , newComputer);
            return message;
        }else {
            throw new  RuntimeException("占用新机器失败");
        }

      /*  SurfInternetRecords surfInternetRecords = new SurfInternetRecords();
        EntityWrapper<SurfInternetRecords> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("computer_id" , oldComputer)
                .eq("consumption_amount",0)
                .eq("uid",uid);
        surfInternetRecords = surfInternetRecordsService.selectOne(entityWrapper);
        long minute = System.currentTimeMillis()/60000 - surfInternetRecords.getStartTime()/60000;
        Integer number = (int)( minute/30 + 1);
        customers.setIsUsed(Const.Is_Not_Used);
        oldcomputer.setIsUsed(Const.Is_Not_Used);
        surfInternetRecords.setEndTime(System.currentTimeMillis());
        surfInternetRecords.setConsumptionAmount(number*oldcomputer.getLevel()/2);

        try {
            logger.info("先下机，在上机");
            customersService.updateById(customers);
            computersService.updateById(oldcomputer);
            surfInternetRecordsService.updateById(surfInternetRecords);
        }catch (Exception e){
            logger.error(e.getMessage() + "");
            throw e;
        }
        updateRedis.UpdateCustomers(uid, customers);
        updateRedis.UpdateComputers(oldComputer , oldcomputer);*/

    }
}
