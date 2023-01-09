package com.itheima.lss.currency.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.lss.currency.entity.Activity;
import com.itheima.lss.currency.entity.User;

import java.util.List;

public interface ActivityService extends IService<Activity> {
    public List<Activity> getAllAct();
    public List<Activity> getLiveAct();
    public Page<Activity> getByPage(int page);
    public List<Activity> getMyAct(User user);
    public Activity getById(int id);
    public boolean releaseAct(Activity activity);
    public List<Activity> getMyReleaseStartToday(User user);
    public List<Activity> getByKeywordAct(String keyword);

}
