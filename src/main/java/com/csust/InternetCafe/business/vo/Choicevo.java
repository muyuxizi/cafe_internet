package com.csust.InternetCafe.business.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @Author: 小凯神
 * @create: 2019-06-08 18:08
 * @Description:
 */
@Builder
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor()
public class Choicevo {

    private Integer start;

    private Integer end;

    private Integer Somking;
}
