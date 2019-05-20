package com.csust.InternetCafe.common.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@TableName("admin")
@Builder
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor()
public class Admin {

    @TableId(value = "id")
    private Integer id;

    @TableId(value = "uid")
    private Long uid;

    @TableId(value = "admin_id")
    private Integer adminId;

    @TableId(value = "admin_department")
    private String adminDepartment;

    @TableId(value = "admin_name")
    private String adminName;
}
