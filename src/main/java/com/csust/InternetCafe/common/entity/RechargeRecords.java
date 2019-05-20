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
 * @Date: 2019-02-27 10:17
 * @Description:
 */

@TableName("recharge_records")
@Builder
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor()
public class RechargeRecords {

    @TableId(value = "id")
    private Integer id;

    @TableId(value = "uid")
    private Long uid;

    @TableId(value = "actual_recharge_money")
    private Integer actualRechargeMoney;

    @TableId(value = "actual_amount_achieved")
    private Integer actualAmountAchieved;

    @TableId(value = "recharge_mode")
    private Integer rechargeMode;

    @TableId(value = "update_time")
    private Long updateTime;
}
