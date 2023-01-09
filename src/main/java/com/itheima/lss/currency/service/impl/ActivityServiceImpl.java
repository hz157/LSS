package com.itheima.lss.currency.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.lss.currency.entity.Activity;
import com.itheima.lss.currency.entity.Book;
import com.itheima.lss.currency.entity.User;
import com.itheima.lss.currency.mapper.ActivityMapper;
import com.itheima.lss.currency.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ActivityServiceImpl extends ServiceImpl<ActivityMapper, Activity> implements ActivityService {

    @Autowired
    ActivityMapper activityMapper;

    @Override
    public List<Activity> getAllAct() {
        List<Activity> activityList = activityMapper.selectList(null);
        return activityList;
    }

    @Override
    public List<Activity> getLiveAct() {
        QueryWrapper queryWrapper = new QueryWrapper();
        // 生成当前时间
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(dateFormat.format(new Date()));
        queryWrapper.gt("deadline",dateFormat.format(new Date()).toString());
        List<Activity> activityList = activityMapper.selectList(queryWrapper);
        return activityList;
    }

    @Override
    public Page<Activity> getByPage(int page) {
        Page<Activity> pages = activityMapper.selectPage(new Page<>(page, 10),null);
        return pages;
    }

    @Override
    public List<Activity> getMyAct(User user) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("user",user.getId());
        List<Activity> activityList = activityMapper.selectList(queryWrapper);
        return activityList;
    }

    @Override
    public Activity getById(int id) {
        return activityMapper.selectById(id);
    }

    @Override
    public boolean releaseAct(Activity activity) {
        if (activityMapper.insert(activity) == 1)
            return true;
        return false;
    }

    @Override
    public List<Activity> getMyReleaseStartToday(User user) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("user",user.getId());
        List<Activity> activityList = activityMapper.selectList(queryWrapper);
        List<Activity> activities = new ArrayList<>();
        for(Activity i : activityList){
            String startDate = i.getStartDate();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            Date datetime = null;
            try {
                datetime = format.parse(startDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if(isToday(datetime))
                activities.add(i);
        }
        return activities;
    }

    public static boolean isToday(Date inputJudgeDate) {
        boolean flag = false;
        //获取当前系统时间
        long longDate = System.currentTimeMillis();
        Date nowDate = new Date(longDate);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = dateFormat.format(nowDate);
        String subDate = format.substring(0, 10);
        //定义每天的24h时间范围
        String beginTime = subDate + " 00:00:00";
        String endTime = subDate + " 23:59:59";
        Date paseBeginTime = null;
        Date paseEndTime = null;
        try {
            paseBeginTime = dateFormat.parse(beginTime);
            paseEndTime = dateFormat.parse(endTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(inputJudgeDate.after(paseBeginTime) && inputJudgeDate.before(paseEndTime)) {
            flag = true;
        }
        return flag;
    }


    @Override
    public List<Activity> getByKeywordAct(String keyword) {
        QueryWrapper qe = new QueryWrapper();
        qe.like("name",keyword);
        List<Activity> activityList = activityMapper.selectList(qe);
        return activityList;
    }
}
