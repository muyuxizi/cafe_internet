package com.csust.InternetCafe.business.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @Author: 小凯神
 * @create: 2019-05-13 18:48
 * @Description:
 */
@Builder
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor()
public class Surfrecordvo {

    private String username;

    private Integer computerId;

    private Long startTime;

    private Long endTime;

    private Integer consumptionAmount;

    private String cafeName;
}
