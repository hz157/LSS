package com.itheima.lss.currency.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.lss.currency.entity.Borrow;
import com.itheima.lss.currency.entity.Stock;
import com.itheima.lss.currency.entity.User;

import java.util.List;

public interface BorrowService extends IService<Borrow> {
    public boolean addRecord(Borrow borrow);
    public List<Borrow> getUserRecord(User user);
    public boolean closeRecord(Borrow borrow);
    public List<Borrow> getUserLiveRecord(User user);
    public Borrow getById(Long id);
}
