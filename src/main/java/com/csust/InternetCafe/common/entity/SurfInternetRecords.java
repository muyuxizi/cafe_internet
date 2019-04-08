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
 * @Date: 2019-02-27 15:01
 * @Description:
 */
@TableName("surf_internet_records")
@Builder
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor()
public class SurfInternetRecords {
    @TableId(value = "id")
    private Integer id;

    @TableId(value = "computer_id")
    private Integer computerId;

    @TableId(value = "uid")
    private Integer uid;

    @TableId(value = "start_time")
    private Integer startTime;

    @TableId(value = "end_time")
    private Integer endTime;

    @TableId(value = "consumption_amount")
    private Integer consumptionAmount;

    @TableId(value = "cafe_name")
    private String cafeName;
}
