package com.itheima.lss.currency.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("lss_activityrecord")
public class ActivityRecord {
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;
    @TableField("activity")
    private Long activity;
    @TableField("user")
    private Long user;
    @TableField("registrationDate")
    private String registrationDate;
    @TableField("attend")
    private int attend;
    @TableField("SignDate")
    private String SignDate;

}
