package com.itheima.lss.currency.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.lss.currency.entity.Borrow;
import com.itheima.lss.currency.entity.Stock;
import com.itheima.lss.currency.entity.User;
import com.itheima.lss.currency.mapper.BorrowMapper;
import com.itheima.lss.currency.service.BorrowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class BorrowServiceImpl extends ServiceImpl<BorrowMapper, Borrow> implements BorrowService {
    @Autowired
    BorrowMapper borrowMapper;

    /**
     * 添加借阅记录
     * @param borrow
     * @return
     */
    @Override
    public boolean addRecord(Borrow borrow) {
        // 生成当前时间
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        borrow.setBorrowingDate(dateFormat.format(new Date()));
        // 判断是否成功添加记录
        if (borrowMapper.insert(borrow) == 1)
            return true;
        return false;
    }

    /**
     * 获取用户所有的借阅记录
     * @param user
     * @return
     */
    @Override
    public List<Borrow> getUserRecord(User user) {
        // 构造sql参数
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("user",user.getId());
        // 查询
        List<Borrow> borrowList = borrowMapper.selectList(queryWrapper);
        return borrowList;
    }

    /**
     * 获取未归还的书籍信息
     * @param user
     * @return
     */
    @Override
    public List<Borrow> getUserLiveRecord(User user) {
        // 构造sql参数
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("user",user.getId());
        // 获取还书日期列为空的数据
        queryWrapper.isNull("returnDate");
        // 查询
        List<Borrow> borrowList = borrowMapper.selectList(queryWrapper);
        return borrowList;
    }

    /**
     * 还书，写入还书时间，截止记录
     * @param borrow
     * @return
     */
    @Override
    public boolean closeRecord(Borrow borrow) {
        // 生成当前时间
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        borrow.setReturnDate(dateFormat.format(new Date()));
        // 查询
        if (borrowMapper.updateById(borrow) == 1)
            return true;
        return false;
    }

    /**
     * 通过借阅ID获取借阅信息
     * @param id
     * @return
     */
    @Override
    public Borrow getById(Long id) {
        return borrowMapper.selectById(id);
    }
}
