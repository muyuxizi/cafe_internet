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
 * @Date: 2019-02-26 18:38
 * @Description:
 */
@TableName("users")
@Builder
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor()
public class Users {
    @TableId(value = "id")
    private Integer id;

    @TableId(value = "uid")
    private Integer uid;

    @TableId(value = "username")
    private String username;

    @TableId(value = "password")
    private String password;

    @TableId(value = "telephone_number")
    private Integer telephoneNumber;

    @TableId(value = "identity_type")
    private Integer identityType;

    @TableId(value = "last_login_time")
    private Long lastLoginTime;

    @TableId(value = "id_card")
    private String idCard;

    @TableId(value = "birthday")
    private String birthday;
}
