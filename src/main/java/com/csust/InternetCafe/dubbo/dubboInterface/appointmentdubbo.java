package com.csust.InternetCafe.dubbo.dubboInterface;

import com.csust.InternetCafe.business.vo.Appointmentvo;

/**
 * @Author: 小凯神
 * @Date: 2019-04-22 15:10
 * @Description:
 */
public interface appointmentdubbo {
    public String appointment(String username , Appointmentvo appointmentvo);

    public String recharge(String username , int money);
}
