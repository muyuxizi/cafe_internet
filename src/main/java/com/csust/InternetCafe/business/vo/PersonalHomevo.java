package com.csust.InternetCafe.business.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @Author: 小凯神
 * @Date: 2019-04-15 17:15
 * @Description:
 */
@Builder
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor()
public class PersonalHomevo {

        private String username;

        private Integer accountMoney;

        private String isUsed;

        private String isAppointment;

        private String telephoneNumber;

        private String birthday;

}
