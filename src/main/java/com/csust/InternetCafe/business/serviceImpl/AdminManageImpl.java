package com.csust.InternetCafe.business.serviceImpl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.csust.InternetCafe.business.service.AdminManage;
import com.csust.InternetCafe.common.entity.Admin;
import com.csust.InternetCafe.common.entity.Users;
import com.csust.InternetCafe.common.service.AdminService;
import com.csust.InternetCafe.common.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: 小凯神
 * @create: 2019-05-18 19:26
 * @Description:
 */
@Service
public class AdminManageImpl implements AdminManage {

    @Resource
    private AdminService adminService;

    @Resource
    private UserService userService;

    @Override
    public List<Admin> get() {
        List<Admin> adminList = new ArrayList<>();
        EntityWrapper<Admin> entityWrapper = new EntityWrapper<>();
        adminList = adminService.selectList(entityWrapper);
        return adminList;
    }

    @Override
    public Admin getone(String name) {
        Admin admin = null;
        EntityWrapper<Admin> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("admin_name" , name);
        admin = adminService.selectOne(entityWrapper);
        return admin;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String add(Admin admin) {
        Long uid = admin.getUid();
        EntityWrapper<Users> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("uid" , uid);
        Users users = userService.selectOne(entityWrapper);
        if(users == null){
            Users users1 = Users.builder()
                    .birthday("1997-08-23")
                    .id(0)
                    .idCard("432525115")
                    .identityType(2)
                    .lastLoginTime(System.currentTimeMillis())
                    .password("123456")
                    .telephoneNumber(uid)
                    .uid(uid)
                    .username("管理员"+uid)
                    .build();
            try {
                userService.insert(users1);
                adminService.insert(admin);
            }catch (Exception e){
                throw  e;
            }
        }else {
            users.setIdentityType(2);
            try {
                userService.updateById(users);
                adminService.insert(admin);
            }catch (Exception e){
                throw  e;
            }
        }

        return "success";
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String update(Admin admin) {
        try {
            adminService.updateById(admin);
        }catch (Exception e){
            throw  e;
        }
        return "success";
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String del(Long uid ,int id) {
        EntityWrapper<Users> usersEntityWrapper = new EntityWrapper<>();
        usersEntityWrapper.eq("uid",uid);
        try {
            userService.delete(usersEntityWrapper);
            adminService.deleteById(id);
        }catch (Exception e){
            throw  e;
        }
        return "success";
    }

}
