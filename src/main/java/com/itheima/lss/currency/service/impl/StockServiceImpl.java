package com.itheima.lss.currency.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.lss.currency.entity.Book;
import com.itheima.lss.currency.entity.Borrow;
import com.itheima.lss.currency.entity.Stock;
import com.itheima.lss.currency.entity.User;
import com.itheima.lss.currency.mapper.StockMapper;
import com.itheima.lss.currency.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class StockServiceImpl extends ServiceImpl<StockMapper, Stock> implements StockService {

    @Autowired
    StockMapper stockMapper;

    /**
     * 通过用户UID查找用户分享的所有书
     * @param user
     * @return
     */
    @Override
    public List<Stock> findBookByUser(User user) {
        // 构造sql请求
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("user",user.getId());
        List<Stock> stocks = stockMapper.selectList(queryWrapper);
        return stocks;
    }

    /**
     * 添加书
     * @param user
     * @param book
     * @return
     */
    @Override
    public boolean addBook(User user, Book book) {
        // 构造实体类
        Stock stock = new Stock();
        stock.setIsbn(book.getIsbn());
        stock.setUser(user.getId());
        // 获取当前时间
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        stock.setCreateTime(dateFormat.format(new Date()));
        stock.setStatus(0);
        if (stockMapper.insert(stock)==1)
            return true;
        return false;
    }

    /**
     * 通过ISBN和用户进行查找一本书
     * @param user
     * @param book
     * @return
     */
    @Override
    public Stock findByIsbnUser(User user, Book book) {
        // 构造sql请求
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("user",user.getId());
        queryWrapper.eq("isbn",book.getIsbn());
        Stock stock = stockMapper.selectOne(queryWrapper);
        return stock;
    }

    /**
     * 获取库存中该ISBN号的所有书
     * @param book
     * @return
     */
    @Override
    public List<Stock> getStock(Book book) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("isbn",book.getIsbn());
        List<Stock> stockList = stockMapper.selectList(queryWrapper);
        return stockList;
    }

    /**
     * 获取库存中可供借阅的书籍信息
     * @param book
     * @return
     */
    @Override
    public List<Stock> getAvailableStock(Book book) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("isbn",book.getIsbn());
        queryWrapper.eq("status",0);
        List<Stock> stockList = stockMapper.selectList(queryWrapper);
        return stockList;
    }

    /**
     * 更新数据库当中书籍状态
     * @param stock
     * @param status 1已借阅、0未借阅
     * @return
     */
    @Override
    public boolean updateStatus(Stock stock,int status) {
        stock.setStatus(status);
//        System.out.println(stock);
        if(stockMapper.updateById(stock) == 1)
            return true;
        return false;
    }

    /**
     * 通过借阅记录实体获取ISBN号
     * @param borrow
     * @return
     */
    @Override
    public String getIsbn(Borrow borrow) {
        Stock stock = stockMapper.selectById(borrow.getBook());
        return stock.getIsbn().toString();
    }

    /**
     * 查找用户分享的书
     * @param user
     * @return
     */
    @Override
    public List<Stock> getByUser(User user) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("user",user.getId());
        return stockMapper.selectList(queryWrapper);
    }
}
