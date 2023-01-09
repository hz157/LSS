package com.itheima.lss.currency.controller;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.lss.currency.config.ResultTemplate;
import com.itheima.lss.currency.entity.*;
import com.itheima.lss.currency.service.ActivityCodeService;
import com.itheima.lss.currency.service.ActivityRecordService;
import com.itheima.lss.currency.service.ActivityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/lss/activity")
public class ActivityController {

    Logger logger = LoggerFactory.getLogger(ActivityController.class);

    @Resource
    ActivityService activityService;

    @Resource
    ActivityCodeService activityCodeService;

    @Resource
    ActivityRecordService activityRecordService;


    /**
     * 获取活动记录
     * @param type
     * @param request
     * @return
     */
    @RequestMapping(value = "getHistory", method = RequestMethod.POST)
    public Object getHistory(@RequestParam(value = "type", required = false)String type, HttpServletRequest request) {
        try {
            User user = (User) request.getAttribute("user");
            logger.info("Request:/lss/activity/getHistory \t id:" + user.getId());
            if(type==null)
                return new ResultTemplate<>(activityService.getAllAct());
            else if(type.equals("live"))
                return new ResultTemplate<>(activityService.getLiveAct());
            return new ResultTemplate<>("400","异常提示");
        }catch (Exception e){
            e.printStackTrace();
            return new ResultTemplate<>("400","异常提示");
        }
    }

    /**
     * 发布活动
     * @param activity
     * @param request
     * @return
     */
    @RequestMapping(value = "release", method = RequestMethod.POST)
    public Object getHistory(@RequestBody Activity activity,HttpServletRequest request) {
        try {
            User user = (User) request.getAttribute("user");
            logger.info("Request:/lss/activity/release \t id:" + user.getId());
            activity.setUser(user.getId());
            if(activity.getDeadline().equals("null"))
                activity.setDeadline(null);
            if (activityService.releaseAct(activity))
                return new ResultTemplate<>("200","发布成功");
            return new ResultTemplate<>("400","异常提示");
        }catch (Exception e){
            e.printStackTrace();
            return new ResultTemplate<>("400","异常提示");
        }
    }

    /**
     * 获取我参加的活动记录
     * @param request
     * @return
     */
    @RequestMapping(value = "getMyRegister", method = RequestMethod.POST)
    public Object getMyRegister(HttpServletRequest request) {
        try{
            User user = (User) request.getAttribute("user");
            logger.info("Request:/lss/activity/add \t id:" + user.getId());
            List<ActivityRecord> activityRecordList = activityRecordService.getByUser(user);
            List<Activity> activityList = new ArrayList<>();
            for(ActivityRecord i : activityRecordList){
                activityList.add(activityService.getById(i.getActivity()));
            }
            return new ResultTemplate<>(activityList);
        }catch (Exception e){
            e.printStackTrace();
            return new ResultTemplate<>("400","异常提示");
        }
    }

    /**
     * 参加活动
     * @param request
     * @param id
     * @return
     */
    @RequestMapping(value = "register", method = RequestMethod.POST)
    public Object register(HttpServletRequest request,@RequestParam("id") String id) {
        try{
            User user = (User) request.getAttribute("user");
            logger.info("Request:/lss/activity/getMyRelease \t id:" + user.getId());
            Activity activity = activityService.getById(id);
            ActivityRecord activityRecord = new ActivityRecord();
            activityRecord.setActivity(activity.getId());
            activityRecord.setUser(user.getId());
            activityRecord.setAttend(0);
            if(activityRecordService.addRecord(activityRecord))
                return new ResultTemplate<>("200","参加成功");
            return new ResultTemplate<>("400","异常提示");
        }catch (Exception e){
            e.printStackTrace();
            return new ResultTemplate<>("400","异常提示");
        }
    }

    /**
     * 获取我发布的活动记录
     * @param request
     * @return
     */
    @RequestMapping(value = "getMyRelease", method = RequestMethod.POST)
    public Object getMyRelease(HttpServletRequest request) {
        try{
            User user = (User) request.getAttribute("user");
            logger.info("Request:/lss/activity/getMyRelease \t id:" + user.getId());
            List<Activity> activityList = activityService.getMyAct(user);
            return new ResultTemplate<>(activityList);
        }catch (Exception e){
            e.printStackTrace();
            return new ResultTemplate<>("400","异常提示");
        }
    }

    /**
     * 生成签到码
     * @param request
     * @param id
     * @return
     */
    @RequestMapping(value = "createCode", method = RequestMethod.POST)
    public Object createCode(HttpServletRequest request, @RequestParam(value = "id")String id) {
        try{
            User user = (User) request.getAttribute("user");
            logger.info("Request:/lss/book/add \t id:" + user.getId());
            Activity activity = activityService.getById(id);
            String code = activityCodeService.createCode(activity);
            return new ResultTemplate<>(code);
        }catch (Exception e){
            e.printStackTrace();
            return new ResultTemplate<>("400","异常提示");
        }
    }

    /**
     * 停用签到码
     * @param request
     * @param id
     * @return
     */
    @RequestMapping(value = "closeCode", method = RequestMethod.POST)
    public Object closeCode(HttpServletRequest request, @RequestParam(value = "id")String id) {
        try{
            User user = (User) request.getAttribute("user");
            logger.info("Request:/lss/book/add \t id:" + user.getId());
            Activity activity = activityService.getById(id);
            if (activityCodeService.closeCode(activity))
                return new ResultTemplate<>("200","签到码已停用");
            return new ResultTemplate<>("400","异常提示");
        }catch (Exception e){
            e.printStackTrace();
            return new ResultTemplate<>("400","异常提示");
        }
    }

    /**
     * 签到
     * @param request
     * @param code
     * @return
     */
    @RequestMapping(value = "signup", method = RequestMethod.POST)
    public Object signup(HttpServletRequest request, @RequestParam(value = "code")String code) {
        try{
            User user = (User) request.getAttribute("user");
            logger.info("Request:/lss/book/add \t id:" + user.getId());
            ActivityCode activityCode = activityCodeService.signup(code);
            Activity activity = activityService.getById(activityCode.getActivity());
            ActivityRecord activityRecord = activityRecordService.getByUserAct(user,activity);
            if (activityRecordService.updateRecord(activityRecord))
                return new ResultTemplate<>("200","签到成功");
            return new ResultTemplate<>("400","异常提示");
        }catch (Exception e){
            e.printStackTrace();
            return new ResultTemplate<>("400","异常提示");
        }
    }

    /**
     * 获取签到码
     * @param request
     * @param id
     * @return
     */
    @RequestMapping(value = "getCode", method = RequestMethod.POST)
    public Object getCode(HttpServletRequest request, @RequestParam(value = "id")String id) {
        try{
            User user = (User) request.getAttribute("user");
            logger.info("Request:/lss/book/add \t id:" + user.getId());
            Activity activity = activityService.getById(id);
            String code = activityCodeService.getCode(activity);
            return new ResultTemplate<>(code);
        }catch (Exception e){
            e.printStackTrace();
            return new ResultTemplate<>("400","异常提示");
        }
    }


    /**
     * 获取活动参与人
     * @param request
     * @param id
     * @return
     */
    @RequestMapping(value = "getRegisterNumber", method = RequestMethod.POST)
    public Object getRegisterNumber(HttpServletRequest request, @RequestParam(value = "id")String id) {
        try{
            User user = (User) request.getAttribute("user");
            logger.info("Request:/lss/book/add \t id:" + user.getId());
            Activity activity = activityService.getById(id);
            List<ActivityRecord> activityRecordList = activityRecordService.getByAct(activity);
            return new ResultTemplate<>(activityRecordList);
        }catch (Exception e){
            e.printStackTrace();
            return new ResultTemplate<>("400","异常提示");
        }
    }

    @RequestMapping(value = "getMyReleaseStartToday", method = RequestMethod.POST)
    public Object getMyReleaseStartToday(HttpServletRequest request) {
        try{
            User user = (User) request.getAttribute("user");
            logger.info("Request:/lss/book/add \t id:" + user.getId());
            List<Activity> activities = activityService.getMyReleaseStartToday(user);
            logger.info(activities.toString());
            return new ResultTemplate<>(activities);
        }catch (Exception e){
            e.printStackTrace();
            return new ResultTemplate<>("400","异常提示");
        }
    }


    @RequestMapping(value = "keywordAct", method = RequestMethod.POST)
    public Object keywordAct(HttpServletRequest request,@RequestParam("keyword")String keyword) {
        try{
            User user = (User) request.getAttribute("user");
            logger.info("Request:/lss/book/add \t id:" + user.getId());
            List<Activity> activities = activityService.getByKeywordAct(keyword);
            logger.info(activities.toString());
            return new ResultTemplate<>(activities);
        }catch (Exception e){
            e.printStackTrace();
            return new ResultTemplate<>("400","异常提示");
        }
    }

    @RequestMapping(value = "getById", method = RequestMethod.POST)
    public Object getById(HttpServletRequest request,@RequestParam("id")String id) {
        try{
            User user = (User) request.getAttribute("user");
            logger.info("Request:/lss/book/getById \t id:" + user.getId());
            Activity activity = activityService.getById(id);
            return new ResultTemplate<>(activity);
        }catch (Exception e){
            e.printStackTrace();
            return new ResultTemplate<>("400","异常提示");
        }
    }
}
