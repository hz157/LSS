package com.itheima.lss.currency.config;


import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ResultTemplate<T> {
    private Date date;
    private String code;
    private String msg;
    private T data;

    public ResultTemplate() {
        this.date = new Date();
        this.code = "200";
        this.msg = "success";
    }

    /**
     * 若没有数据返回，可以人为指定状态码和提示信息
     * @param code
     * @param msg
     */
    public ResultTemplate(String code, String msg) {
        this.date = new Date();
        this.code = code;
        this.msg = msg;
    }

    /**
     * 有数据返回时，状态码为0，默认提示信息为：操作成功！
     * @param data
     */
    public ResultTemplate(T data) {
        this.date = new Date();
        this.code = "200";
        this.msg = "success";
        this.data = data;

    }

    /**
     * 有数据返回，状态码为0，人为指定提示信息
     * @param data
     * @param msg
     */
    public ResultTemplate(T data, String msg) {
        this.date = new Date();
        this.code = "200";
        this.msg = msg;
        this.data = data;
    }
}
