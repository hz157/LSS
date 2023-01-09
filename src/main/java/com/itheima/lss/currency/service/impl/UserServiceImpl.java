package com.itheima.lss.currency.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.lss.currency.entity.RoleEnum;
import com.itheima.lss.currency.entity.SexEnum;
import com.itheima.lss.currency.entity.User;
import com.itheima.lss.currency.entity.UserInfo;
import com.itheima.lss.currency.mapper.UserMapper;
import com.itheima.lss.currency.service.UserService;
import com.itheima.lss.currency.util.Encry;
import com.itheima.lss.currency.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    UserMapper userMapper;

    /**
     * 用户登录请求
     * @param tel
     * @param pwd
     * @return
     */
    @Override
    public User login(String tel, String pwd) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("tel",tel);
        queryWrapper.eq("pwd",new Encry().Md5EnCode(pwd));
        User user = userMapper.selectOne(queryWrapper);
        return user;
    }

    /**
     * 通过ID查找账号
     * @param id
     * @return
     */
    @Override
    public User findById(String id) {
        User user = userMapper.selectById(Long.parseLong(id));
        if (user != null)
            return user;
        else
            return null;
    }

    /**
     * 通过电话号码查找账号
     * @param tel
     * @return
     */
    @Override
    public User findByTel(String tel) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("tel",tel);
        User user = userMapper.selectOne(queryWrapper);
        if (user != null)
            return user;
        else
            return null;
    }

    @Override
    public List<User> findAllUser() {
        List<User> users = userMapper.selectList(null);
        if (users != null)
            return users;
        else
            return null;
    }

    /**
     * 添加用户
     * @param data
     * @return
     */
    @Override
    public boolean addUser(JSONObject data) {
        User user = new User();
        user.setPwd(new Encry().Md5EnCode(data.get("pwd").toString()));
        user.setTel(data.get("tel").toString());
        user.setRole(RoleEnum.User);

        if (userMapper.insert(user) == 1)
            return true;
        return false;

    }

    /**
     * 验证用户是否存在
     * @param tel
     * @return
     */
    @Override
    public boolean verify(String tel) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("tel",tel);
        User user = userMapper.selectOne(queryWrapper);
        if (user != null)
            return true;
        return false;
    }

    /**
     * 修改密码
     * @param id
     * @param pwd
     * @return
     */
    @Override
    public boolean forgotPwd(String id, String pwd) {
        User user = userMapper.selectById(Long.parseLong(id));
        user.setPwd(new Encry().Md5EnCode(pwd));
        if(userMapper.updateById(user) == 1)
            return true;
        return false;
    }

    @Override
    public boolean editPwd(User user) {
        if (userMapper.updateById(user) == 1)
            return true;
        return false;
    }
}


