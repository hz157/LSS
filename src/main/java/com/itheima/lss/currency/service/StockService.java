package com.itheima.lss.currency.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.lss.currency.entity.Book;
import com.itheima.lss.currency.entity.Borrow;
import com.itheima.lss.currency.entity.Stock;
import com.itheima.lss.currency.entity.User;

import java.util.List;

public interface StockService extends IService<Stock> {
    public List<Stock> findBookByUser(User user);
    public boolean addBook(User user, Book book);
    public Stock findByIsbnUser(User user,Book book);
    public List<Stock> getStock(Book book);
    public List<Stock> getAvailableStock(Book book);
    public boolean updateStatus(Stock stock,int status);
    public String getIsbn(Borrow borrow);
    public List<Stock> getByUser(User user);
}
