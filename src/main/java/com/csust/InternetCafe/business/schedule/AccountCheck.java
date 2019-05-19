package com.csust.InternetCafe.business.schedule;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.csust.InternetCafe.business.service.Activation;
import com.csust.InternetCafe.common.commonconst.Const;
import com.csust.InternetCafe.common.commonconst.RedisOrSelect;
import com.csust.InternetCafe.common.entity.Computers;
import com.csust.InternetCafe.common.entity.Customers;
import com.csust.InternetCafe.common.entity.SurfInternetRecords;
import com.csust.InternetCafe.common.entity.Users;
import com.csust.InternetCafe.common.service.CustomersService;
import com.csust.InternetCafe.common.service.SurfInternetRecordsService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
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


}
