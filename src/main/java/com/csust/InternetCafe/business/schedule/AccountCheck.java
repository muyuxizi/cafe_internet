package com.csust.InternetCafe.business.schedule;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * @Author: 小凯神
 * @create: 2019-05-16 19:29
 * @Description:
 */
@Service
public class AccountCheck {

    @Scheduled(cron = "0 0 * * * ?")
    private void accountCheck(){

    }
}
