package com.itheima.lss.currency.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("lss_activitysigncode")
public class ActivityCode {
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;
    @TableField("activity")
    private Long activity;
    @TableField("code")
    private String code;
    @TableField("deadline")
    private String deadline;
}
