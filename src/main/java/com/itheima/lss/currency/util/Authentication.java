package com.itheima.lss.currency.util;

import com.itheima.lss.currency.entity.RoleEnum;
import com.itheima.lss.currency.entity.User;

public class Authentication {

    /**
     * 管理员认证
     * @param user
     * @return
     */
    public boolean isAdmin(User user){
        if (user.getRole().equals(RoleEnum.Admin))
            return true;
        return false;
    }

    /**
     * 普通用户识别
     * @param user
     * @return
     */
    public boolean isUser(User user){
        if (user.getRole().equals(RoleEnum.User))
            return true;
        return false;
    }
}
