package com.itheima.lss.currency.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.lss.currency.entity.User;


import java.util.List;

public interface UserService extends IService<User> {
    public User login(String tel,String pwd);
    public User findById(String id);
    public User findByTel(String tel);
    public List<User> findAllUser();
    public boolean addUser(JSONObject data);
    public boolean verify(String tel);
    public boolean forgotPwd(String id,String pwd);
    public boolean editPwd(User user);
}
