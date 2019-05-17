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
 * @create: 2019-05-16 18:32
 * @Description:
 */
@TableName("everyday_bill")
@Builder
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor()
public class EverydayBill {

    @TableId(value = "id")
    private Integer id;

    @TableId(value = "surf_internet_profit")
    private Integer surfInternetProfit;

    @TableId(value = "sale_profit")
    private Integer saleProfit;

    @TableId(value = "time")
    private String time;
}
