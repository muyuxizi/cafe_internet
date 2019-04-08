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
 * @Date: 2019-02-27 20:48
 * @Description:
 */
@TableName("permission")
@Builder
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor()
public class Permission {
    @TableId(value = "id")
    private Integer id;

    @TableId(value = "code")
    private String code;

    @TableId(value = "name")
    private String name;

    @TableId(value = "role")
    private Integer role;
}
