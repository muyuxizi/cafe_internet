package com.csust.InternetCafe.business.serviceImpl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.csust.InternetCafe.business.service.PersonalHome;
import com.csust.InternetCafe.business.vo.PersonalHomevo;
import com.csust.InternetCafe.common.commonconst.RedisOrSelect;
import com.csust.InternetCafe.common.commonconst.UpdateRedis;
import com.csust.InternetCafe.common.entity.AdminOperationRecords;
import com.csust.InternetCafe.common.entity.Appointment;
import com.csust.InternetCafe.common.entity.Customers;
import com.csust.InternetCafe.common.entity.Users;
import com.csust.InternetCafe.common.service.AdminOperarionRecordsService;
import com.csust.InternetCafe.common.service.CustomersService;
import com.csust.InternetCafe.common.service.RedisService;
import com.csust.InternetCafe.common.service.UserService;
import com.google.gson.Gson;
import org.apache.catalina.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: 小凯神
 * @Date: 2019-04-15 17:28
 * @Description:
 */
@Service
public class PersonalHomeImpl implements PersonalHome {

    @Resource
    private UpdateRedis updateRedis;

    @Resource
    private RedisService redisService;

    @Resource
    private RedisOrSelect redisOrSelect;

    @Resource
    private CustomersService customersService;

    @Resource
    private UserService userService;


    @Resource
    private AdminOperarionRecordsService adminOperarionRecordsService;

    private static Logger logger = LogManager.getLogger("HelloLog4j");

    @Override
    public PersonalHomevo getinformation(String username) {
        Users users = redisOrSelect.findUsers(username);
        int uid = users.getUid();
        Customers customers = redisOrSelect.findCustomers(uid);
        PersonalHomevo personalHomevo = PersonalHomevo.builder()
                .accountMoney(customers.getAccountMoney())
                .birthday(users.getBirthday())
                .isAppointment(customers.getIsAppointment())
                .isUsed(customers.getIsUsed())
                .telephoneNumber(String.valueOf(users.getTelephoneNumber()))
                .username(username)
                .build();

        return personalHomevo;
    }

    @Override
    public List<PersonalHomevo> getcustomerslist() {
        List<PersonalHomevo> personalHomevos = new ArrayList<>();
        List<Customers> customersList = new ArrayList<>();
        EntityWrapper<Customers> entityWrapper = new EntityWrapper<>();
        customersList = customersService.selectList(entityWrapper);
        for(Customers customers : customersList){
            Users users = redisOrSelect.findUsers(customers.getUid());
            PersonalHomevo personalHomevo = PersonalHomevo.builder()
                    .accountMoney(customers.getAccountMoney())
                    .birthday(users.getBirthday())
                    .isAppointment(customers.getIsAppointment())
                    .isUsed(customers.getIsUsed())
                    .telephoneNumber(String.valueOf(users.getTelephoneNumber()))
                    .username(users.getUsername())
                    .build();
            personalHomevos.add(personalHomevo);
        }
        return personalHomevos;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String update(String username ,PersonalHomevo personalHomevo) {
        Users admin = redisOrSelect.findUsers(username);
        AdminOperationRecords adminOperationRecords = AdminOperationRecords.builder()
                .adminId(admin.getUid())
                .operationDetails("更新用户")
                .operationReason("更新用户")
                .operationSurface("customers")
                .updateTime(System.currentTimeMillis())
                .id(0)
                .build();

        Users users = redisOrSelect.findUsers(personalHomevo.getUsername());
        users.setBirthday(personalHomevo.getBirthday());
        users.setTelephoneNumber(Integer.valueOf(personalHomevo.getTelephoneNumber()));
        Customers customers = redisOrSelect.findCustomers(users.getUid());
        customers.setIsAppointment(personalHomevo.getIsAppointment());
        customers.setIsUsed(personalHomevo.getIsUsed());
        customers.setAccountMoney(personalHomevo.getAccountMoney());
        try {
            adminOperarionRecordsService.insert(adminOperationRecords);
            customersService.updateById(customers);
            userService.updateById(users);
            updateRedis.UpdateCustomers(users.getUid() , customers);
            return "success";
        }catch (Exception e){
            throw e;
        }

    }
}
