package com.csust.InternetCafe.common.commonconst;

import com.csust.InternetCafe.common.entity.Customers;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

/**
 * @Author: 小凯神
 * @Date: 2019-04-15 16:27
 * @Description:
 */
@Service
public class Const {

    private static Logger logger = LogManager.getLogger("HelloLog4j");

    public static final int Money_QIAN = 1000;
    public static final int Money_BAI = 100;
    public static final int Money_SHI = 10;
    public static final int Give_Money_QIAN = 2000;
    public static final int Give_Money_Bai = 100;
    public static final int Give_Money_SHI = 5;
    public static final int Computer_Is_Normal = 1;
    public static final int Computer_Is_Fault = 2;

    public static final String Redis_User = "redis_user";
    public static final String Redis_Computer = "redis_computer";
    public static final int Customers_Identity = 1;
    public static final int Admin_Identity = 2;
    public static final String Redis_Customers = "redis_customers";
    public static final String Redis_Admin = "redis_admin";

    public static final String Is_Not_Appointment = "no";
    public static final String Is_Appointment = "yes";
    public static final String Is_Not_Used = "no";
    public static final String Is_Used = "yes";


    public int moneyConverter(int money){
        int add = 0;
        int sum = 0;
        if(money/Money_QIAN != 0){
            add = Give_Money_QIAN * (money/Money_QIAN);
        }else {
            if(money/Money_BAI != 0){
                add = Give_Money_Bai * (money/Money_BAI);
            }else {
                if(money/Money_SHI != 0){
                    add = Give_Money_SHI * (money/Money_SHI);
                }
            }
        }
       
        sum = add + money;
        return sum;
    }

    public int converter(int in){
        if(in == 1) {
            return 2;
        }else {
            return 1;
        }
    }

}
