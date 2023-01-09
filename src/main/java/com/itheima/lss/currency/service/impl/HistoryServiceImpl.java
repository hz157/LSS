package com.itheima.lss.currency.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.lss.currency.entity.Book;
import com.itheima.lss.currency.entity.History;
import com.itheima.lss.currency.entity.User;
import com.itheima.lss.currency.mapper.HistoryMapper;
import com.itheima.lss.currency.service.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class HistoryServiceImpl extends ServiceImpl<HistoryMapper, History> implements HistoryService {
    @Autowired
    HistoryMapper historyMapper;

    /**
     * 全查找
     * @param user
     * @return
     */
    @Override
    public List<History> findByPage(User user) {
        QueryWrapper qe = new QueryWrapper();
        qe.eq("user",user.getId());
        qe.select().orderByDesc("datetime");
        List<History> historyList = historyMapper.selectList(qe);
        return historyList;
    }

    /**
     * 分页查找
     * @param user
     * @param page
     * @return
     */
    @Override
    public Page<History> findByPage(User user, int page) {
        QueryWrapper qe = new QueryWrapper();
        qe.eq("user",user.getId());
        qe.select().orderByDesc("datetime");
        Page<History> historyPage = historyMapper.selectPage(new Page<>(page, 10),qe);
        return historyPage;
    }

    /**
     * 插入历史数据
     * @param user
     * @param book
     * @return
     */
    @Override
    public boolean addHistory(User user, Book book) {
        QueryWrapper qe = new QueryWrapper();
        qe.eq("user",user.getId());
        qe.eq("isbn",book.getIsbn());
        History history = historyMapper.selectOne(qe);
        // 生成当前时间
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (history == null){
            history = new History();
            history.setUser(user.getId());
            history.setIsbn(book.getIsbn());
            history.setDatetime(dateFormat.format(new Date()));
            if (historyMapper.insert(history) == 1)
                return true;
        }else{
            history.setDatetime(dateFormat.format(new Date()));
            if (historyMapper.updateById(history) == 1)
                return true;
        }
        return false;
    }
}
