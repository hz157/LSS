package com.itheima.lss.currency.entity;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;
import lombok.Setter;

@Getter
public enum SexEnum {

    Male(1, "男"),
    Female(0, "女");

    @EnumValue
    private int sex;
    private String value;

    SexEnum(Integer code, String value) {
        this.sex = code;
        this.value = value;
    }
}
