package com.csust.InternetCafe.common.commonconst;

import org.springframework.stereotype.Service;

/**
 * @Author: 小凯神
 * @Date: 2019-04-15 16:27
 * @Description:
 */
@Service
public class Const {

    public int converter(int in){
        if(in == 1) {
            return 2;
        }else {
            return 1;
        }
    }

}
