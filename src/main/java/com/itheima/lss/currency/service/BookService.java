package com.itheima.lss.currency.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.lss.currency.entity.Book;

import java.util.List;

public interface BookService extends IService<Book> {
    public boolean addBook(Book book, String isbn);

    public List<Book> findAll();

    public Book findByIsbn(String isbn);
    public Page<Book> findByPage(int page);
}
