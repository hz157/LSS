package com.itheima.lss.currency.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("lss_userinfo")
public class UserInfo {
    @TableId(value = "id")
    private Long id;

    @TableField("username")
    private String username;

    @TableField("actual")
    private String actualname;

    @TableField("sex")
    private SexEnum sex;

    @TableField("avatar")
    private String avatar;

    @TableField("address")
    private String address;

    @TableField("mail")
    private String mail;

    @TableField("tel")
    private String tel;

    @TableField("idcard")
    private String idcard;

    @TableField("integral")
    private Double integral;
}
