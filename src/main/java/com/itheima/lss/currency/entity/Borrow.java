package com.itheima.lss.currency.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("lss_borrow")
public class Borrow {
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;
    @TableField("book")
    private Long book;
    @TableField("user")
    private Long user;
    @TableField("borrowingDate")
    private String borrowingDate;
    @TableField("returnDate")
    private String returnDate;
}
