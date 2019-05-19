package com.csust.InternetCafe.business.serviceImpl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.csust.InternetCafe.business.service.Recharge;
import com.csust.InternetCafe.common.commonconst.Const;
import com.csust.InternetCafe.common.commonconst.RedisOrSelect;
import com.csust.InternetCafe.common.entity.*;
import com.csust.InternetCafe.common.service.CustomersService;
import com.csust.InternetCafe.common.service.EveryBillService;
import com.csust.InternetCafe.common.service.RechargeRecordsService;
import com.csust.InternetCafe.common.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.Calendar;

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

    @Resource
    private EveryBillService everyBillService;

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

        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int date = c.get(Calendar.DATE);
        String time = year + "-" + month + "-" +date;
        EntityWrapper<EverydayBill> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("time",time);
        EverydayBill everydayBill = everyBillService.selectById(entityWrapper);
        if(everydayBill == null){
            EverydayBill todayBill = EverydayBill.builder()
                    .id(0)
                    .saleProfit(0)
                    .surfInternetProfit(money)
                    .time(time)
                    .build();
            try {
                everyBillService.insert(todayBill);
                rechargeRecordsService.insert(rechargeRecords);
            }catch (Exception e){
                logger.error(e.getMessage()+"");
                throw e;
            }

        }else {
                everydayBill.setSurfInternetProfit(everydayBill.getSurfInternetProfit() + money);
            try {
                everyBillService.updateById(everydayBill);
                rechargeRecordsService.insert(rechargeRecords);
            } catch (Exception e) {
                logger.error(e.getMessage() + "");
                throw e;
            }

        }

        return "success";
    }
}
