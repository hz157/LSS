package com.itheima.lss.currency.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.lss.currency.entity.Activity;
import com.itheima.lss.currency.entity.ActivityRecord;
import com.itheima.lss.currency.entity.User;

import java.util.List;

public interface ActivityRecordService extends IService<ActivityRecord> {
    public List<ActivityRecord> getByUser(User user);

    public List<ActivityRecord> getByAct(Activity activity);

    public boolean updateRecord(ActivityRecord activityRecord);

    public boolean addRecord(ActivityRecord activityRecord);

    public ActivityRecord getByUserAct(User user, Activity activity);
}
