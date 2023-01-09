package com.itheima.lss.currency.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("lss_activity")
public class Activity {
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;
    @TableField("poster")
    private String poster;
    @TableField("name")
    private String name;
    @TableField("address")
    private String address;
    @TableField("peopleLimit")
    private int peopleLimit;
    @TableField("registrationDate")
    private String registrationDate;
    @TableField("deadline")
    private String deadline;

    @TableField("startDate")
    private String startDate;
    @TableField("user")
    private Long user;

}
