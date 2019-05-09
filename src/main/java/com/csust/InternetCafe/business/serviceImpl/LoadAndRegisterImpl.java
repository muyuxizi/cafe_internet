package com.csust.InternetCafe.business.serviceImpl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.csust.InternetCafe.business.service.LoadAndRegister;
import com.csust.InternetCafe.business.vo.Registervo;
import com.csust.InternetCafe.common.entity.Admin;
import com.csust.InternetCafe.common.entity.Customers;
import com.csust.InternetCafe.common.entity.Permission;
import com.csust.InternetCafe.common.entity.Users;
import com.csust.InternetCafe.common.service.AdminService;
import com.csust.InternetCafe.common.service.CustomersService;
import com.csust.InternetCafe.common.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Calendar;

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

        int birthdayyear = Integer.valueOf(registervo.getBirthday().substring(0 , 3));
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        if((year-birthdayyear)<18){
            return "Sorry!您还未成年！暂时不可以上网!";
        }


        int i = registervo.getPassWord().matches(".*\\d+.*") ? 1 : 0;
        int j = registervo.getPassWord().matches(".*[a-zA-Z]+.*") ? 1 : 0;
        int k = registervo.getPassWord().matches(".*[~!@#$%^&*()_+|<>,.?/:;'\\[\\]{}\"]+.*") ? 1 : 0;
        int l = registervo.getPassWord().length();

        //用户名重复验证
        EntityWrapper<Users> queryWrapper = new  EntityWrapper<>();
        queryWrapper.eq("username" , registervo.getUserName());
        Users users1 = userService.selectOne(queryWrapper);
        if(users1 != null){
            return "对不起，该名字已经存在，请换个名字";
        }

        //手机号码重复验证
        EntityWrapper<Users> queryWrapper1 = new  EntityWrapper<>();
        queryWrapper1.eq("telephone_number" , registervo.getPassWord());
        Users users2 = userService.selectOne(queryWrapper1);
        if(users2 != null){
            return "对不起，该手机号已经注册过了";
        }

        //密码验证
        if(i + j + k < 2 || l < 6 || l > 16 ) {
            return "请输入6-16位数字字母或者数字特殊字符组合密码";
        }

        //两次密码验证
        if(!registervo.getPassWord().equals(registervo.getRePassWord())){
            return "输入的两次密码不同";
        }


        Users users = Users.builder()
                .id(0)
                .lastLoginTime(null)
                .uid(Integer.valueOf(registervo.getTelephone()))
                .birthday(registervo.getBirthday())
                .username(registervo.getUserName())
                .password( new BCryptPasswordEncoder().encode(registervo.getPassWord()))
                .idCard(registervo.getIdCard())
                .identityType(1)
                .telephoneNumber(registervo.getTelephone())
                .build();
        if(users.getIdentityType()==1){

            Customers customers = Customers.builder()
                    .id(0)
                    .accountMoney(0)
                    .uid(Integer.valueOf(registervo.getTelephone()))
                    .isAppointment("no")
                    .isUsed("no")
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
                    .id(0)
                    .adminId(null)
                    .adminDepartment("department")
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
