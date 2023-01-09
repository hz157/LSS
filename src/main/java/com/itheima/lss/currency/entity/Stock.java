package com.itheima.lss.currency.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("lss_stock")
public class Stock {
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;
    @TableField("user")
    private Long user;
    @TableField("isbn")
    private Long isbn;
    @TableField("createtime")
    private String createTime;
    @TableField("status")
    private Integer status;
}
