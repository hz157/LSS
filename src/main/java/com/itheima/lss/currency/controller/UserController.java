package com.itheima.lss.currency.controller;

import com.alibaba.fastjson.JSONObject;
import com.itheima.lss.currency.entity.User;
import com.itheima.lss.currency.entity.UserInfo;
import com.itheima.lss.currency.service.UserInfoService;
import com.itheima.lss.currency.service.UserService;
import com.itheima.lss.currency.config.ResultTemplate;
import com.itheima.lss.currency.util.Encry;
import com.itheima.lss.currency.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/lss/user")
public class UserController {

    Logger logger = LoggerFactory.getLogger(UserController.class);

    @Resource
    UserService userService;

    @Resource
    UserInfoService userInfoService;

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public Object login(@RequestBody JSONObject json, HttpServletResponse response){
        try{
            // 这里的id是手机号码
            String id = json.get("id").toString();
            // 密码
            String pwd = json.get("pwd").toString();
            String token = null;
            // 验证账号密码是否正确
            User user = userService.login(id,pwd);
            // user有值表示账号密码正确，创建Token返回给客户端
            if (user != null){
                String userId = UUID.randomUUID().toString();
                Map<String, Object> info = new HashMap<>();
                info.put("id", user.getId());
                info.put("pwd", pwd);
                //生成JWT字符串
                token = JwtUtil.sign(userId, info);
                // Token为空 生成失败
                if (token == null)
                    return new ResultTemplate<>("400","Token生成失败,请重新发起请求");
                logger.info("id: " + user.getId() + "\t token:" + token);
                return new ResultTemplate<>(token);
            }
            // 没进入IF说明 账号密码验证失败
            return new ResultTemplate<>("400","登录失败,可能是账号或密码错误");
        }catch (Exception e){
            return new ResultTemplate<>("400","异常提示");
        }
    }

    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public Object register(@RequestBody JSONObject json){
        try{
            String tel = json.get("tel").toString();
            logger.info("register: " + tel);
            if (userService.verify(tel))
                return new ResultTemplate<>("400","账号已存在");
            if (!userService.addUser(json))
                return new ResultTemplate<>("400","账号添加错误");
            User user = userService.findByTel(tel);
            if (!userInfoService.addUserInfo(user, json))
                return new ResultTemplate<>("400","个人资料添加出错");
//            return new ResultTemplate<>("200",user.getId() + "注册成功");
            return new ResultTemplate<>("200","注册成功");
        }catch (Exception e){
            e.printStackTrace();
            return new ResultTemplate<>("400","异常提示");
        }
    }

    @RequestMapping(value = "/verify",method = RequestMethod.POST)
    public Object veryfiy(@RequestParam("tel")String tel){
        try{
            if (userService.verify(tel))
                return new ResultTemplate<>("200","手机号已存在");
            return new ResultTemplate<>("200","账号可用");
        }catch (Exception e){
            e.printStackTrace();
            return new ResultTemplate<>("400","异常提示");
        }
    }

    @RequestMapping(value = "/forgot",method = RequestMethod.POST)
    public Object forgot(@RequestBody JSONObject json){
        try{
            String idcard = json.get("idcard").toString();
            String tel = json.get("tel").toString();
            String pwd = json.get("pwd").toString();
            UserInfo userInfo = userInfoService.findByIdCard(idcard);
            if (userInfo == null)
                return new ResultTemplate<>("400","查找不该身份证");
            User user = userService.findById(userInfo.getId().toString());
            if (!user.getTel().equals(tel))
                return new ResultTemplate<>("400","手机号与身份证不匹配");
            if(userService.forgotPwd(user.getId().toString(),pwd))
                return new ResultTemplate<>("200","修改成功");
            return new ResultTemplate<>("400","修改失败");
        }catch (Exception e){
            e.printStackTrace();
            return new ResultTemplate<>("400","异常提示");
        }
    }


}
