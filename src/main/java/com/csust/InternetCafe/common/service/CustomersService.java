package com.csust.InternetCafe.common.service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.csust.InternetCafe.common.entity.Customers;
import com.csust.InternetCafe.common.mapper.CustomersMapper;
import org.springframework.stereotype.Service;

/**
 * @Author: 小凯神
 * @Date: 2019-02-26 22:27
 * @Description:
 */
@Service
public class CustomersService extends ServiceImpl<CustomersMapper , Customers> {
}
