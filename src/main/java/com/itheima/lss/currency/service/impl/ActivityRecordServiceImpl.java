package com.itheima.lss.currency.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.lss.currency.entity.Activity;
import com.itheima.lss.currency.entity.ActivityRecord;
import com.itheima.lss.currency.entity.User;
import com.itheima.lss.currency.mapper.ActivityRecordMapper;
import com.itheima.lss.currency.service.ActivityRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class ActivityRecordServiceImpl extends ServiceImpl<ActivityRecordMapper, ActivityRecord> implements ActivityRecordService {

    @Autowired
    ActivityRecordMapper activityRecordMapper;

    @Override
    public List<ActivityRecord> getByUser(User user) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("user",user.getId());
        return(activityRecordMapper.selectList(queryWrapper));
    }

    @Override
    public List<ActivityRecord> getByAct(Activity activity) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("activity",activity.getId());
        return(activityRecordMapper.selectList(queryWrapper));
    }

    @Override
    public boolean updateRecord(ActivityRecord activityRecord) {
        // 生成当前时间
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        activityRecord.setSignDate(dateFormat.format(new Date()));
        activityRecord.setAttend(1);
        if (activityRecordMapper.updateById(activityRecord) == 1)
            return true;
        return false;
    }

    @Override
    public boolean addRecord(ActivityRecord activityRecord) {
        // 生成当前时间
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        activityRecord.setRegistrationDate(dateFormat.format(new Date()));
        if (activityRecordMapper.insert(activityRecord) == 1)
            return true;
        return false;
    }

    @Override
    public ActivityRecord getByUserAct(User user, Activity activity) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("activity",activity.getId());
        queryWrapper.eq("user",user.getId());
        return(activityRecordMapper.selectOne(queryWrapper));
    }
}
