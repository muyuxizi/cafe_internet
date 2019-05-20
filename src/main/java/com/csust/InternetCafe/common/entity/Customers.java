package com.csust.InternetCafe.common.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @Author: 小凯神
 * @Date: 2019-02-26 22:11
 * @Description:
 */
@TableName("customers")
@Builder
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor()
public class Customers {

    @TableId(value = "id")
    private Integer id;

    @TableId(value = "uid")
    private Long uid;

    @TableId(value = "account_money")
    private Integer accountMoney;

    @TableId(value = "is_used")
    private String isUsed;

    @TableId(value = "is_appointment")
    private String isAppointment;
}
