package com.itheima.lss.currency.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.lss.currency.entity.Book;
import com.itheima.lss.currency.mapper.BookMapper;
import com.itheima.lss.currency.service.BookService;
import com.itheima.lss.currency.util.APIs.IsbnApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl extends ServiceImpl<BookMapper, Book> implements BookService {

    IsbnApi isbnApi = new IsbnApi();

    @Autowired
    BookMapper bookMapper;

    @Override
    public boolean addBook(Book book, String isbn) {
       if (book == null)
           book = isbnApi.API(isbn);
       try{
           if (bookMapper.insert(book) == 1)
               return true;
       }catch (Exception e){
           return false;
       }
       return false;
    }

    @Override
    public List<Book> findAll() {
        List<Book> books = bookMapper.selectList(null);
        return books;
    }

    @Override
    public Book findByIsbn(String isbn) {
        return bookMapper.selectById(Long.parseLong(isbn));
    }


    @Override
    public Page<Book> findByPage(int page) {
        Page<Book> pages = bookMapper.selectPage(new Page<>(page, 10),null);
        return pages;
    }
}
