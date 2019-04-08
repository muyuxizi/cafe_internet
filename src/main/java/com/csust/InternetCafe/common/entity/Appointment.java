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
 * @Date: 2019-02-27 9:45
 * @Description:
 */

@TableName("appointment")
@Builder
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor()
public class Appointment {

    @TableId(value = "id")
    private Integer id;

    @TableId(value = "uid")
    private Integer uid;

    @TableId(value = "computer_id")
    private Integer computerId;

    @TableId(value = "cafe_name")
    private String cafeName;

    @TableId(value = "appointment_time_slot")
    private String appointmentTimeSlot;

    @TableId(value = "appointment_date")
    private String appointmentDate;

    @TableId(value = "amount")
    private Integer amount;
}
