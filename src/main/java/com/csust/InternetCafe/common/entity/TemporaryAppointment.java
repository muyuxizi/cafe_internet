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
 * @Date: 2019-02-27 15:16
 * @Description:
 */
@TableName("temporary_appointment")
@Builder
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor()
public class TemporaryAppointment {
    @TableId(value = "id")
    private Integer id;

    @TableId(value = "uid")
    private Long uid;

    @TableId(value = "computer_id")
    private Integer computerId;

    @TableId(value = "cafe_name")
    private String cafeName;

    @TableId(value = "appointment_start_time")
    private Integer appointmentStartTime;

    @TableId(value = "appointment_end_time")
    private Integer appointmentEndTime;

    @TableId(value = "amount")
    private Integer amount;
}
