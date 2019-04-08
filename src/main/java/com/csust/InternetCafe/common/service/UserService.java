package com.csust.InternetCafe.common.service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.csust.InternetCafe.common.entity.Users;
import com.csust.InternetCafe.common.mapper.UsersMapper;
import org.springframework.stereotype.Service;

/**
 * @Author: 小凯神
 * @Date: 2019-02-26 18:54
 * @Description:
 */
@Service
public class UserService extends ServiceImpl<UsersMapper , Users> {
}
