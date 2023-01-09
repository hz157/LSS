package com.itheima.lss.currency.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.lss.currency.entity.Activity;
import com.itheima.lss.currency.entity.ActivityCode;

public interface ActivityCodeService extends IService<ActivityCode> {
    public String createCode(Activity activity);
    public boolean closeCode(Activity activity);
    public String getCode(Activity activity);
    public ActivityCode signup(String code);
}
