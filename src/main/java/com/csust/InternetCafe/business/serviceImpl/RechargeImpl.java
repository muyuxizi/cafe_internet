package com.csust.InternetCafe.business.serviceImpl;

import com.csust.InternetCafe.business.service.Recharge;
import com.csust.InternetCafe.common.commonconst.Const;
import com.csust.InternetCafe.common.commonconst.RedisOrSelect;
import com.csust.InternetCafe.common.entity.Customers;
import com.csust.InternetCafe.common.entity.RechargeRecords;
import com.csust.InternetCafe.common.entity.Users;
import com.csust.InternetCafe.common.service.CustomersService;
import com.csust.InternetCafe.common.service.RechargeRecordsService;
import com.csust.InternetCafe.common.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigInteger;

/**
 * @Author: 小凯神
 * @Date: 2019-04-16 15:29
 * @Description:
 */
@Service
public class RechargeImpl implements Recharge {

    private static Logger logger = LogManager.getLogger("HelloLog4j");

    @Resource
    private RechargeRecordsService rechargeRecordsService;

    @Resource
    private UserService userService;

    @Resource
    private CustomersService customersService;

    @Resource
    private Const aConst;

    @Resource
    private RedisOrSelect redisOrSelect;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String recharge(String username, int money) {

        if(money < 0 || money > 9999){return  "请输入大于0小于10000的充值金额";}

        int actualRechargeMoney = aConst.moneyConverter(money);

        Users users = redisOrSelect.findUsers(username);
        int uid = users.getUid();
        Customers customers = redisOrSelect.findCustomers(uid);
        customers.setAccountMoney(customers.getAccountMoney() + actualRechargeMoney);
        try {
            customersService.updateById(customers);
        }catch (Exception e){
            logger.error(e.getMessage()+"");
            throw e;
        }

        RechargeRecords rechargeRecords = RechargeRecords.builder()
                .rechargeMode(4)
                .actualRechargeMoney(money)
                .actualAmountAchieved(actualRechargeMoney)
                .uid(uid)
                .id(0)
                .updateTime(System.currentTimeMillis())
                .build();
        try {
            rechargeRecordsService.insert(rechargeRecords);
        }catch (Exception e){
            logger.error(e.getMessage()+"");
            throw e;
        }



        return "success";
    }
}
