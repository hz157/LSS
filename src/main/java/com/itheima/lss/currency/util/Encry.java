package com.itheima.lss.currency.util;


import org.apache.commons.codec.digest.DigestUtils;


/**
 * 加密工具类
 */
public class Encry {
    public String Md5EnCode(String text){
        System.out.println(DigestUtils.md5Hex("774"+text+"@@__123!"));
        return DigestUtils.md5Hex("774"+text+"@@__123!");
    }
}
