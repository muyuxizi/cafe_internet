package com.csust.InternetCafe.common.pojo;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.csust.InternetCafe.business.service.Initialization;
import com.csust.InternetCafe.common.commonconst.Const;
import com.csust.InternetCafe.common.entity.Permission;
import com.csust.InternetCafe.common.entity.Users;
import com.csust.InternetCafe.common.service.PermissionService;
import com.csust.InternetCafe.common.service.RedisService;
import com.csust.InternetCafe.common.service.UserService;
import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import sun.rmi.runtime.NewThreadAction;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @Author: 小凯神
 * @Date: 2019-02-27 16:00
 * @Description:
 */
@Service
public class MyUserDetailsService implements UserDetailsService {

    @Resource
    private UserService userService;

    @Resource
    private Const constserver;

    @Resource
    private RedisService redisService;

    @Resource
    private Initialization initialization;

    @Resource
    private PermissionService permissionService;

    private static Logger logger = LogManager.getLogger("HelloLog4j");

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        EntityWrapper<Users> newWrapper = new  EntityWrapper<>();
        newWrapper.eq("username" , username);
        Users users = userService.selectOne(newWrapper);
        if (users == null) {
            throw new UsernameNotFoundException(username);
            }else {
            //将用户信息加入到Redis
            String value = new Gson().toJson(users);
            redisService.set(Const.Redis_User+username , value);
            logger.info("已将" + users.toString() + "加入到缓存");
        }

        //将用户具体信息加载到Redis
        initialization.LoadToRedis(users.getUid() , users.getIdentityType());
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        Integer role = users.getIdentityType();
        List<Permission> list = new ArrayList<>();
        EntityWrapper<Permission> queryWrapper = new  EntityWrapper<>();
        if(role == 4){
            queryWrapper.eq("role" , 4);
        }else {
        queryWrapper.ne("role" ,constserver.converter(role));
        }
        list = permissionService.selectList(queryWrapper);
        for(Permission permission : list){
            authorities.add(new SimpleGrantedAuthority(permission.getCode()));
        }

        return new User(users.getUsername() , new BCryptPasswordEncoder().encode(users.getPassword()) , authorities);
    }





}
