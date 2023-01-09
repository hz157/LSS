package com.itheima.lss.currency.controller;

import com.alibaba.fastjson.JSONObject;
import com.itheima.lss.currency.config.ResultTemplate;
import com.itheima.lss.currency.entity.RoleEnum;
import com.itheima.lss.currency.entity.User;
import com.itheima.lss.currency.entity.UserInfo;
import com.itheima.lss.currency.service.UserInfoService;
import com.itheima.lss.currency.service.UserService;
import com.itheima.lss.currency.util.Authentication;
import com.itheima.lss.currency.util.Encry;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/lss/userInfo")
public class UserInfoController {
    Logger logger = LoggerFactory.getLogger(UserController.class);
    Authentication authentication = new Authentication();
    @Resource
    UserInfoService userInfoService;

    @Resource
    UserService userService;

    @RequestMapping(value = "/getInfo",method = RequestMethod.POST)
    public Object getInfo(HttpServletRequest request){
        try{
            User user = (User) request.getAttribute("user");
            logger.info("Request:/lss/userInfo/getInfo \t id:" + user.getId());
            UserInfo userInfo = userInfoService.findById(user.getId().toString());
            return new ResultTemplate<>(userInfo);
        }catch (Exception e){
            e.printStackTrace();
            return new ResultTemplate<>("400","异常提示");
        }
    }
    @RequestMapping(value = "/getInfoAdmin",method = RequestMethod.POST)
    public Object getAllInfo(@RequestParam(value = "id", required = false)String id, HttpServletRequest request) {
        try{
            User user = (User) request.getAttribute("user");
            logger.info("Request:/lss/userInfo/getInfoAdmin \t id:" + user.getId());
            // 如果获取他人或者其他全部用户信息，进行鉴权，管理员允许返回，普通用户提示无权限
            if (!authentication.isAdmin(user))
                return new ResultTemplate<>("400","无权操作");
            // 没有传递参数查询全部数据
            if(id == null){
                List<UserInfo> userInfoList = userInfoService.findAllInfo();
                if (userInfoList != null)
                    return new ResultTemplate<>(userInfoList);
            }
            // 单用户查找
            UserInfo userInfo = userInfoService.findById(id);
            if (userInfo != null)
                return new ResultTemplate<>(userInfo);
            return new ResultTemplate<>("200","数据查询出错");
        }catch (Exception e){
            e.printStackTrace();
            return new ResultTemplate<>("400","异常提示");
        }
    }
    @RequestMapping(value = "/editPwd",method = RequestMethod.POST)
    public Object editPwd(@RequestBody JSONObject json, HttpServletRequest request){
        try{
            User user = (User) request.getAttribute("user");
            logger.info("Request:/lss/userInfo/editPwd \t id:" + user.getId());
            if(!user.getPwd().equals(new Encry().Md5EnCode(json.get("oldPwd").toString())))
                return new ResultTemplate<>("400","原密码有误");
            user.setPwd(new Encry().Md5EnCode(json.get("newPwd").toString()));
            if (userService.editPwd(user))
                return new ResultTemplate<>("200","修改成功");
            return new ResultTemplate<>("400","修改失败");
        }catch (Exception e){
            e.printStackTrace();
            return new ResultTemplate<>("400","异常提示");
        }
    }

    @RequestMapping(value = "/editProfile",method =  RequestMethod.POST)
    public Object editProfile(@RequestBody JSONObject json, HttpServletRequest request){
        try{
            User user = (User) request.getAttribute("user");
            logger.info("Request:/lss/userInfo/editProfile \t id:" + user.getId());
            UserInfo userInfo = userInfoService.findById(user.getId().toString());
            userInfo.setUsername(json.get("username").toString());
            userInfo.setMail(json.get("mail").toString());
            userInfo.setAddress(json.get("address").toString());
            if(userInfoService.EditUserInfo(userInfo))
                return new ResultTemplate<>("200","修改成功");
            return new ResultTemplate<>("400","修改失败");
        }catch (Exception e){
            e.printStackTrace();
            return new ResultTemplate<>("400","异常提示");
        }
    }
    @RequestMapping(value = "/editAvatar",method =  RequestMethod.POST)
    public Object editAvatar(@RequestParam(value = "path")String path, HttpServletRequest request){
        try{
            User user = (User) request.getAttribute("user");
            logger.info("Request:/lss/userInfo/editProfile \t id:" + user.getId());
            UserInfo userInfo = userInfoService.findById(user.getId().toString());
            userInfo.setAvatar(path);
            if(userInfoService.EditUserInfo(userInfo))
                return new ResultTemplate<>("200","修改成功");
            return new ResultTemplate<>("400","修改失败");
        }catch (Exception e){
            e.printStackTrace();
            return new ResultTemplate<>("400","异常提示");
        }
    }

    @RequestMapping(value = "/addIntegral",method =  RequestMethod.POST)
    public Object addIntegral(@RequestParam(value = "type")String type, HttpServletRequest request){
        boolean result = false;
        try{
            User user = (User) request.getAttribute("user");
            logger.info("Request:/lss/userInfo/editProfile \t id:" + user.getId());
            UserInfo userInfo = userInfoService.findById(user.getId().toString());
            if(type.equals("0")){
                userInfo.setIntegral(userInfo.getIntegral() + 10);
                if(userInfoService.addIntegral(userInfo))
                    result = true;
            }else if (type.equals("1")){
                userInfo.setIntegral(userInfo.getIntegral() + 20);
                if(userInfoService.addIntegral(userInfo))
                    result = true;
            }
            if(result)
                return new ResultTemplate<>("200","修改成功");
            return new ResultTemplate<>("400","修改失败");
        }catch (Exception e){
            e.printStackTrace();
            return new ResultTemplate<>("400","异常提示");
        }
    }


}
