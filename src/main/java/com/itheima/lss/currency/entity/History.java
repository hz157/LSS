package com.itheima.lss.currency.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.extern.java.Log;

import java.lang.ref.PhantomReference;

@Data
@TableName("lss_history")
public class History {

    @TableId(value = "id",type = IdType.AUTO)
    private Long id;
    @TableField("isbn")
    private Long isbn;
    @TableField("user")
    private Long user;
    @TableField("datetime")
    private String datetime;
}
