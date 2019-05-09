package com.csust.InternetCafe.business.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @Author: 小凯神
 * @create: 2019-05-09 10:37
 * @Description:
 */
@Builder
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor()
public class TemporaryAppointmentvo {

    private String username;

    private Integer computerId;

    private String cafeName;

    private Integer startTime;

    private Integer endTime;

    private Integer amount;
}
