package com.itheima.lss.currency.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@TableName("lss_book")
public class Book {
    @TableId(value = "id")
    private Long isbn;
    @TableField("name")
    private String name;
    @TableField("subname")
    private String subname;
    @TableField("photoUrl")
    private String photoUrl;
    @TableField("description")
    private String description;
    @TableField("author")
    private String author;
    @TableField("authorIntro")
    private String authorIntro;
    @TableField("translator")
    private String translator;
    @TableField("publishing")
    private String publishing;
    @TableField("published")
    private String published;
    @TableField("pages")
    private Integer pages;
    @TableField("doubanScore")
    private Integer doubanScore;
}
