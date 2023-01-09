package com.itheima.lss.currency.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.lss.currency.entity.Book;
import com.itheima.lss.currency.entity.History;
import com.itheima.lss.currency.entity.User;
import com.itheima.lss.currency.mapper.HistoryMapper;

import java.util.List;

public interface HistoryService extends IService<History> {
    public List<History> findByPage(User user);
    public Page<History> findByPage(User user, int page);

    public boolean addHistory(User user, Book book);
}
