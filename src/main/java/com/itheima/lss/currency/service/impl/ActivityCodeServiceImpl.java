package com.itheima.lss.currency.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.lss.currency.entity.Activity;
import com.itheima.lss.currency.entity.ActivityCode;
import com.itheima.lss.currency.entity.User;
import com.itheima.lss.currency.mapper.ActivityCodeMapper;
import com.itheima.lss.currency.service.ActivityCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

@Service
public class ActivityCodeServiceImpl extends ServiceImpl<ActivityCodeMapper, ActivityCode> implements ActivityCodeService {

    @Autowired
    ActivityCodeMapper activityCodeMapper;


    @Override
    public String createCode(Activity activity) {
        ActivityCode activityCode = new ActivityCode();
        activityCode.setActivity(activity.getId());
        // 判断数据库中是否已经有验证码了
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("activity",activityCode.getActivity());
        queryWrapper.isNull("deadline");
        ActivityCode activityCode2 = activityCodeMapper.selectOne(queryWrapper);
        if(activityCode2 != null)
            return activityCode2.getCode();

        // 新建验证码
        String random = String.valueOf((int) ((Math.random() * 9 + 1) * 100000));
        ActivityCode activityCode1 = new ActivityCode();
        // 判断是否存在相同的验证码
        do {
            random = String.valueOf((int) ((Math.random() * 9 + 1) * 100000));
            QueryWrapper qw = new QueryWrapper();
            qw.eq("code",random);
            activityCode1 = activityCodeMapper.selectOne(qw);
        }while (activityCode1 != null && (activityCode1.getDeadline()!=null || !activityCode1.getDeadline().equals("null")) );
        activityCode.setCode(random);

        System.out.println(activityCode.toString());
        if (activityCodeMapper.insert(activityCode) == 1)
            return random;
        return null;
    }



    @Override
    public boolean closeCode(Activity activity) {
        // 生成当前时间
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("activity",activity.getId());
        queryWrapper.isNull("deadline");
        ActivityCode activityCode = activityCodeMapper.selectOne(queryWrapper);
        activityCode.setDeadline(dateFormat.format(new Date()));
        if (activityCodeMapper.updateById(activityCode) == 1)
            return true;
        return false;
    }

    @Override
    public String getCode(Activity activity) {
        // 生成当前时间
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("activity",activity.getId());
        queryWrapper.isNull("deadline");
//        queryWrapper.gt("deadline",dateFormat.format(new Date()));
        ActivityCode activityCode = activityCodeMapper.selectOne(queryWrapper);
        String code;
        if(activityCode == null)
            code =  createCode(activity);
        else
            code = activityCode.getCode();
        return code;
    }

    @Override
    public ActivityCode signup(String code) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("code",code);
        queryWrapper.isNull("deadline");
        ActivityCode activityCode = activityCodeMapper.selectOne(queryWrapper);
        return activityCode;
    }
}
