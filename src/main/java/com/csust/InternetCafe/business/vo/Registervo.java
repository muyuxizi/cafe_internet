package com.csust.InternetCafe.business.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @Author: 小凯神
 * @Date: 2019-04-11 9:25
 * @Description:
 */
@Builder
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor()
public class Registervo {

        private String userName;

        private String passWord;

        private String rePassWord;

        private Long telephone;

        private String idCard;

        private String birthday;

}
