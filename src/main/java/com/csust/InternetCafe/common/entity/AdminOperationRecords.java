package com.csust.InternetCafe.common.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigInteger;

/**
 * @Author: 小凯神
 * @Date: 2019-02-27 9:28
 * @Description:
 */

@TableName("admin_operation_records")
@Builder
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor()
public class AdminOperationRecords {

    @TableId(value = "id")
    private Integer id;

    @TableId(value = "admin_id")
    private Long adminId;

    @TableId(value = "operation_surface")
    private String operationSurface;

    @TableId(value = "operation_details")
    private String operationDetails;

    @TableId(value = "update_time")
    private Long updateTime;

    @TableId(value = "operation_reason")
    private String operationReason;
}
