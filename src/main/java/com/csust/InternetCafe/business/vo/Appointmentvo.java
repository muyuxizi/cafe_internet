package com.csust.InternetCafe.business.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @Author: 小凯神
 * @Date: 2019-04-18 10:56
 * @Description:
 */
@Builder
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor()
public class Appointmentvo {

    private Integer computerId;

    private Integer startTime;

    private Integer endTime;


}
