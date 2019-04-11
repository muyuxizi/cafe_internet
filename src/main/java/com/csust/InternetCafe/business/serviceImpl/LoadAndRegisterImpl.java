package com.csust.InternetCafe.business.serviceImpl;

import com.csust.InternetCafe.business.service.LoadAndRegister;
import com.csust.InternetCafe.business.vo.Registervo;
import com.csust.InternetCafe.common.entity.Admin;
import com.csust.InternetCafe.common.entity.Customers;
import com.csust.InternetCafe.common.entity.Users;
import com.csust.InternetCafe.common.service.AdminService;
import com.csust.InternetCafe.common.service.CustomersService;
import com.csust.InternetCafe.common.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @Author: 小凯神
 * @Date: 2019-04-11 9:18
 * @Description:
 */
@Service
public class LoadAndRegisterImpl implements LoadAndRegister {

    private static Logger logger = LogManager.getLogger("HelloLog4j");

    @Resource
    private UserService userService;

    @Resource
    private CustomersService customersService;

    @Resource
    private AdminService adminService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String  Register(Registervo registervo) {

        int i = registervo.getPassWord().matches(".*\\d+.*") ? 1 : 0;
        int j = registervo.getPassWord().matches(".*[a-zA-Z]+.*") ? 1 : 0;
        int k = registervo.getPassWord().matches(".*[~!@#$%^&*()_+|<>,.?/:;'\\[\\]{}\"]+.*") ? 1 : 0;
        int l = registervo.getPassWord().length();
        if(i + j + k < 2 || l < 6 || l > 16 ) {
            return "请输入6-16位数字字母或者数字特殊字符组合密码";
        }
        if(!registervo.getPassWord().equals(registervo.getRePassWord())){
            return "输入的两次密码不同";
        }
        Users users = Users.builder()
                .uid(Integer.valueOf(registervo.getIdCard()))
                .birthday(registervo.getBirthday())
                .username(registervo.getUserName())
                .password(registervo.getPassWord())
                .idCard(registervo.getIdCard())
                .identityType(registervo.getIdentityType())
                .telephoneNumber(registervo.getTelephone())
                .build();
        if(registervo.getIdentityType()==1){
            Customers customers = Customers.builder()
                    .accountMoney(0)
                    .uid(Integer.valueOf(registervo.getIdCard()))
                    .build();
                    try{
                        userService.insert(users);
                        customersService.insert(customers);
                    }catch (Exception e){
                        logger.error(e.getMessage());
                        throw e;
                    }
        }else {
            Admin admin = Admin.builder()
                    .uid(Integer.valueOf(registervo.getIdCard()))
                    .adminName(registervo.getUserName())
                    .build();
                    try {
                        userService.insert(users);
                        adminService.insert(admin);
                    }catch (Exception e){
                        logger.error(e.getMessage()+"");
                        throw e;
                    }
        }
        return "success";
    }


}
