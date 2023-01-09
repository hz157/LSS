package com.itheima.lss.currency.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("lss_user")
public class User {
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;
    @TableField("pwd")
    private String pwd;
    @TableField("tel")
    private String tel;
    @TableField("role")
    private RoleEnum role;
}
