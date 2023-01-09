package com.itheima.lss.currency.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.lss.currency.entity.SexEnum;
import com.itheima.lss.currency.entity.User;
import com.itheima.lss.currency.entity.UserInfo;
import com.itheima.lss.currency.mapper.UserInfoMapper;
import com.itheima.lss.currency.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {

    @Autowired
    UserInfoMapper userInfoMapper;

    /**
     * 查找所有用户资料
     * @return
     */
    @Override
    public List<UserInfo> findAllInfo() {
        List<UserInfo> userInfoList = userInfoMapper.selectList(null);
        if (userInfoList != null)
            return userInfoList;
        return null;
    }

    /**
     * 通过ID查找用户资料
     * @param id
     * @return
     */
    @Override
    public UserInfo findById(String id) {
        UserInfo userInfo = userInfoMapper.selectById(Long.parseLong(id));
        if (userInfo != null)
            return userInfo;
        return null;
    }

    /**
     * 编辑用户资料
     * @param userInfo
     * @return
     */
    @Override
    public boolean EditUserInfo(UserInfo userInfo) {
        if (userInfoMapper.updateById(userInfo) >=1 )
            return true;
        return false;
    }


    /**
     * 添加用户资料
     * @param data
     * @return
     */
    @Override
    public boolean addUserInfo(User user, JSONObject data) {
        UserInfo userInfo = new UserInfo();
        userInfo.setId(user.getId());
        String username = data.get("username").toString();
        String actualName = data.get("actual").toString();
        String sex = data.get("sex").toString();
        String idCard = data.get("idcard").toString();
        userInfo.setUsername(username);
        userInfo.setActualname(actualName);
        userInfo.setSex(SexEnum.Female);
        if (sex.equals("男"))
            userInfo.setSex(SexEnum.Male);
        userInfo.setAvatar(null);
        userInfo.setAddress(null);
        userInfo.setMail(null);
        userInfo.setIdcard(idCard);
        if (userInfoMapper.insert(userInfo) == 1)
            return true;
        return false;
    }

    /**
     * 根据身份证查找用户
     * @param idcard
     * @return
     */
    @Override
    public UserInfo findByIdCard(String idcard) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("idcard",idcard);
        UserInfo userInfo = userInfoMapper.selectOne(queryWrapper);
        return userInfo;
    }

    @Override
    public boolean addIntegral(UserInfo userInfo) {
        if(userInfoMapper.updateById(userInfo) == 1)
            return true;
        return false;
    }
}
