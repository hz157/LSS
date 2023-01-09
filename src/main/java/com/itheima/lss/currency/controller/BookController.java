package com.itheima.lss.currency.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.lss.currency.config.ResultTemplate;
import com.itheima.lss.currency.entity.Book;
import com.itheima.lss.currency.entity.User;
import com.itheima.lss.currency.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/lss/book")
public class BookController {

    Logger logger = LoggerFactory.getLogger(BookController.class);

    @Resource
    BookService bookService;


    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Object add(@RequestBody(required = false) Book book, @RequestParam(value = "isbn", required = false)String isbn, HttpServletRequest request){
        try{
            if(book != null && isbn != null)
                return new ResultTemplate<>("400","禁止同时传递两个参数");
            User user = (User) request.getAttribute("user");
            logger.info("Request:/lss/book/add \t id:" + user.getId());
            boolean resutl = false;
            System.out.println(isbn);
            if(book == null)
                resutl = bookService.addBook(null,isbn);
            else
                resutl = bookService.addBook(book,null);
            if (resutl)
                return new ResultTemplate<>("200","新增图书成功");
            return new ResultTemplate<>("400", "新增图书失败");
        }catch (Exception e){
            e.printStackTrace();
            return new ResultTemplate<>("400","异常提示");
        }
    }


    @RequestMapping(value = "/getInfo", method = RequestMethod.POST)
    public Object getAll(HttpServletRequest request, @RequestParam(value = "isbn",required = false)String isbn){
        try{
            User user = (User) request.getAttribute("user");
            logger.info("Request:/lss/book/add \t id:" + user.getId());
            if (isbn == null) {
                List<Book> books = bookService.findAll();
                return new ResultTemplate<>(books);
            }
            Book book = bookService.findByIsbn(isbn);
//        logger.info(book.toString());
            if(book == null)
                bookService.addBook(null,isbn);
            book = bookService.findByIsbn(isbn);
            logger.info(book.toString());
            return new ResultTemplate<>(book);
        }catch (Exception e){
            e.printStackTrace();
            return new ResultTemplate<>("400","异常提示");
        }
    }
    @RequestMapping(value = "/getPageInfo", method = RequestMethod.POST)
    public Object getPageInfo(HttpServletRequest request, @RequestParam(value = "page")int page) {
        try{
            User user = (User) request.getAttribute("user");
            logger.info("Request:/lss/book/add \t id:" + user.getId());
            Page<Book> pages = bookService.findByPage(page);
            return new ResultTemplate<>(pages);
        }catch (Exception e){
            e.printStackTrace();
            return new ResultTemplate<>("400","异常提示");
        }
    }


}
