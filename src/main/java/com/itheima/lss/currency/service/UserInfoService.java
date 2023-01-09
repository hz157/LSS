package com.itheima.lss.currency.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.lss.currency.entity.User;
import com.itheima.lss.currency.entity.UserInfo;

import java.util.List;

public interface UserInfoService extends IService<UserInfo> {
    List<UserInfo> findAllInfo();
    UserInfo findById(String id);
    UserInfo findByIdCard(String idcard);
    boolean EditUserInfo(UserInfo userInfo);
    boolean addUserInfo(User user, JSONObject data);
    boolean addIntegral(UserInfo userInfo);

}
