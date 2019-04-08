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
 * @Date: 2019-02-26 21:50
 * @Description:
 */
@TableName("computers")
@Builder
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor()
public class Computers {

    @TableId(value = "id")
    private Integer id;

    @TableId(value = "computer_id")
    private Integer computerId;

    @TableId(value = "is_appointment")
    private String isAppointment;

    @TableId(value = "is_used")
    private String isUsed;

    @TableId(value = "is_damaged")
    private Integer isDamaged;

    @TableId(value = "cafe_name")
    private String cafeName;

    @TableId(value = "level")
    private Integer level;

    @TableId(value = "is_somking")
    private Integer isSomking;
}
