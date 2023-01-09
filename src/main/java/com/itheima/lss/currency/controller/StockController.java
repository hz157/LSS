package com.itheima.lss.currency.controller;

import com.itheima.lss.currency.config.ResultTemplate;
import com.itheima.lss.currency.entity.*;
import com.itheima.lss.currency.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/lss/stock")
public class StockController {
    Logger logger = LoggerFactory.getLogger(StockController.class);

    @Resource
    BookService bookService;

    @Resource
    StockService stockService;

    @Resource
    BorrowService borrowService;

    @Resource
    HistoryService historyService;

    /**
     * 用户分享书籍
     * @param request
     * @param isbn
     * @return
     */
    @RequestMapping(value = "/sharebook",method = RequestMethod.POST)
    public Object shareBook(HttpServletRequest request, @RequestParam("isbn")String isbn){
        try{
            User user = (User) request.getAttribute("user");
            logger.info("Request:/lss/stock/add \t id:" + user.getId());
            // 查找书籍信息
            Book book = bookService.findByIsbn(isbn);
            if (stockService.addBook(user,book))
                return new ResultTemplate<>("200","书籍发布成功");
            return new ResultTemplate<>("400","书籍发布失败");
        }catch (Exception e){
            e.printStackTrace();
            return new ResultTemplate<>("400","异常提示");
        }
    }

    /**
     * 判断书籍是否存在该书库
     * @param request
     * @param isbn
     * @return
     */
    @RequestMapping(value = "isexist",method = RequestMethod.POST)
    public Object isExist(HttpServletRequest request,@RequestParam("isbn")String isbn){
        try {
            User user = (User) request.getAttribute("user");
            logger.info("Request:/lss/stock/isexist \t id:" + user.getId());
            // 查找书籍信息
            Book book = bookService.findByIsbn(isbn);
            // 添加书籍库存记录
            Stock stock = stockService.findByIsbnUser(user,book);
            if (stock != null)
                return new ResultTemplate<>("400","您已有该书，是否继续添加");
            return new ResultTemplate<>("200","您的库存中找不到该书");
        }catch (Exception e){
            e.printStackTrace();
            return new ResultTemplate<>("400","异常提示");
        }
    }

    /**
     * 获取该书在书库中的全部
     * @param request
     * @param isbn
     * @return
     */
    @RequestMapping(value = "getStock",method = RequestMethod.POST)
    public Object getStock(HttpServletRequest request,@RequestParam("isbn")String isbn){
        try{
            User user = (User) request.getAttribute("user");
            logger.info("Request:/lss/stock/isexist \t id:" + user.getId());
            // 查找书籍信息
            Book book = bookService.findByIsbn(isbn);
            return new ResultTemplate<>(stockService.getStock(book));
        }catch (Exception e){
            e.printStackTrace();
            return new ResultTemplate<>("400","异常提示");
        }
    }

    /**
     * 获取可借阅的书籍信息
     * @param request
     * @param isbn
     * @return
     */
    @RequestMapping(value = "getAvailableStock",method = RequestMethod.POST)
    public Object getAvailableStock(HttpServletRequest request,@RequestParam("isbn")String isbn){
        try{
            User user = (User) request.getAttribute("user");
            logger.info("Request:/lss/stock/isexist \t id:" + user.getId());
            // 查找书籍信息
            Book book = bookService.findByIsbn(isbn);
            return new ResultTemplate<>(stockService.getAvailableStock(book));
        }catch (Exception e){
            e.printStackTrace();
            return new ResultTemplate<>("400","异常提示");
        }
    }

    /**
     * 借阅书籍
     * @param request
     * @param isbn 书籍ISBN号
     * @return
     */
    @RequestMapping(value = "borrow",method = RequestMethod.POST)
    public Object borrow(HttpServletRequest request,@RequestParam("isbn")String isbn){
        try{
            boolean result1 = false, result2 = false;
            User user = (User) request.getAttribute("user");
            logger.info("Request:/lss/stock/borrow \t id:" + user.getId());
            // 查找书籍信息
            Book book = bookService.findByIsbn(isbn);
            // 获取可供借阅的书籍列表
            List<Stock> stockList = stockService.getAvailableStock(book);
            if(stockList != null){
                // 更熟在库书籍状态
                result1 = stockService.updateStatus(stockList.get(0),1);
                // 构造借阅记录实体类
                Borrow record = new Borrow();
                record.setBook(stockList.get(0).getId());
                record.setUser(user.getId());
                result2 = borrowService.addRecord(record);
            }
            if (result1 && result2)
                return  new ResultTemplate<>("200","借阅成功");
            return new ResultTemplate<>("400","暂无该书可用的共享书籍");
        }catch (Exception e){
            e.printStackTrace();
            return new ResultTemplate<>("400","异常提示");
        }
    }

    /**
     * 获取借阅记录
     * @param request
     * @param type 不携带该参数 或 携带该参数值为live
     * @return
     */
    @RequestMapping(value = "borrowRecord",method = RequestMethod.POST)
    public Object borrowRecord(HttpServletRequest request,@RequestParam(value = "type",required = false)String type){
        try{
            User user = (User) request.getAttribute("user");
            logger.info("Request:/lss/stock/borrowRecord \t id:" + user.getId());
            if(type == null){
                // 获取借阅记录
                List<Borrow> borrowList = borrowService.getUserRecord(user);
                return new ResultTemplate<>(borrowList);
            } else if (type.equals("live")) {
                // 获取未归还的借阅记录
                List<Borrow> borrowList = borrowService.getUserLiveRecord(user);
                return new ResultTemplate<>(borrowList);
            }
            return new ResultTemplate<>("400","异常提示");
        }catch (Exception e){
            e.printStackTrace();
            return new ResultTemplate<>("400","异常提示");
        }
    }

    /**
     * 归还书籍
     * @param request
     * @param bid 借阅ID
     * @return
     */
    @RequestMapping(value = "returnBook",method = RequestMethod.POST)
    public Object returnBook(HttpServletRequest request,@RequestParam("bid")String bid){
        try{
            boolean result1 = false, result2 = false;
            User user = (User) request.getAttribute("user");
            logger.info("Request:/lss/stock/returnBook \t id:" + user.getId());

            Borrow borrow = borrowService.getById(bid);
            Stock stock = stockService.getById(borrow.getBook());

            if(borrow.getReturnDate()==null || stock.getStatus()==1){
                result1 = borrowService.closeRecord(borrow);
                result2 = stockService.updateStatus(stock,0);
                if (result1 && result2)
                    return new ResultTemplate<>("200","归还成功");
            }else{
                if(borrow.getReturnDate()!=null)
                    borrowService.closeRecord(borrow);
                if(stock.getStatus()!=0)
                    stockService.updateStatus(stock,0);
                return new ResultTemplate<>("400","已归还请勿重复归还");
            }
            return new ResultTemplate<>("400","归还异常");
        }catch (Exception e){
            e.printStackTrace();
            return new ResultTemplate<>("400","异常提示");
        }
    }

    /**
     * 通过借阅记录获取图书信息
     * @param request
     * @param id 借阅ID
     * @return
     */
    @RequestMapping(value = "stockGetInfo",method = RequestMethod.POST)
    public Object stockGetInfo(HttpServletRequest request,@RequestParam("id")String id){
        try{
            boolean result1 = false, result2 = false;
            User user = (User) request.getAttribute("user");
            logger.info("Request:/lss/stock/returnBook \t id:" + user.getId());
            Borrow borrow = borrowService.getById(id);
            Book book = bookService.findByIsbn(stockService.getIsbn(borrow));
            return new ResultTemplate<>(book);
        }catch (Exception e){
            e.printStackTrace();
            return new ResultTemplate<>("400","异常提示");
        }
    }

    @RequestMapping(value = "/getMyStock", method = RequestMethod.POST)
    public Object getMyStock(HttpServletRequest request) {
        try{
            User user = (User) request.getAttribute("user");
            logger.info("Request:/lss/stock/getMyBook \t id:" + user.getId());
            List<Stock> stockList = stockService.getByUser(user);
            return new ResultTemplate<>(stockList);
        }catch (Exception e){
            e.printStackTrace();
            return new ResultTemplate<>("400","异常提示");
        }
    }

    @RequestMapping(value = "/getHistory", method = RequestMethod.POST)
    public Object getHistory(HttpServletRequest request) {
        try{
            User user = (User) request.getAttribute("user");
            logger.info("Request:/lss/stock/getHistory \t id:" + user.getId());
            List<History> historyList = historyService.findByPage(user);
            return new ResultTemplate<>(historyList);
        }catch (Exception e){
            e.printStackTrace();
            return new ResultTemplate<>("400","异常提示");
        }
    }

    @RequestMapping(value = "/addHistory", method = RequestMethod.POST)
    public Object addHistory(HttpServletRequest request,@RequestParam("isbn")String isbn) {
        try{
            User user = (User) request.getAttribute("user");
            Book book = bookService.findByIsbn(isbn);
            logger.info("Request:/lss/stock/addHistory \t id:" + user.getId());
            if (historyService.addHistory(user,book))
                return new ResultTemplate<>("200","添加成功");
            return new ResultTemplate<>("400","异常提示");
        }catch (Exception e){
            e.printStackTrace();
            return new ResultTemplate<>("400","异常提示");
        }
    }
}
