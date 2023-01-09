package com.itheima.lss.currency.util.APIs;


import com.alibaba.fastjson.JSONObject;
import com.itheima.lss.currency.entity.Book;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IsbnApi {
    private final String host = "https://api.jike.xyz/situ/book/isbn/";
    private final String join = "?apikey=";
    private final String key = "..210e829672ca5938efe4948932c251fc";

    public Book API(String isbn){
        String result = "";
        // 初始化全空数据
        Book book = initData();
        BufferedReader in = null;
        try {
            String url = host + isbn + join + key;
            URL realUrl = new URL(url);
            URLConnection connection = realUrl.openConnection();
            // 请求头部
            connection.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
            connection.setRequestProperty("Connection", "keep-alive");
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/107.0.0.0 Safari/537.36");
            // 建立实际请求
            connection.connect();
            Map<String, List<String>> map = connection.getHeaderFields();
            for (String key : map.keySet()) {
                System.out.println(key + "---->" + map.get((key)));
            }
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while  ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e){
            System.out.println("发送Get请求异常" + e);
            e.printStackTrace();
        } finally {
            try {
                if (in != null)
                    in.close();
            } catch (Exception e2){
                e2.printStackTrace();
            }
        }
        // JSON拆分
        JSONObject json = JSONObject.parseObject(result);
        // 内层数据JSON
        // System.out.println(json.get("data").toString());
        JSONObject webData = JSONObject.parseObject(json.get("data").toString());
        String[] keyword = new String[]{"isbn","name","subname","photoUrl","description","author","authorIntro","translator","publishing","published","pages","doubanScore"};
        String[] data = new String[12];
        data[0] = isbn;
        setData(book,keyword[0],data[0]);
        for (int i=1;i< data.length;i++){
            if (webData.get(keyword[i]) == null || webData.get(keyword[i]).toString().equals("") || webData.get(keyword[i]).toString().equals("null"))
                data[i] = null;
            else
                data[i] = webData.get(keyword[i]).toString();
            setData(book,keyword[i],data[i]);
        }
        return book;
    }

    public static void setData(Book book, String keyword, String data){
        if (data==null)
            return;
        switch (keyword){
            case "isbn":
                book.setIsbn(Long.parseLong(data));
                break;
            case "name":
                book.setName(data);
                break;
            case "subname":
                book.setSubname(data);
                break;
            case "photoUrl":
                book.setPhotoUrl(data);
                break;
            case "description":
                book.setDescription(data);
                break;
            case "author":
                book.setAuthor(data);
                break;
            case "authorIntro":
                book.setAuthorIntro(data);
                break;
            case "translator":
                book.setTranslator(data);
                break;
            case "publishing":
                book.setPublishing(data);
                break;
            case "published":
                book.setPublished(data);
                break;
            case "pages":
                try{
                    book.setPages(Integer.parseInt(data));
                }catch (Exception e){
                    book.setPages(null);
                }
                break;
            case "doubanScore":
                try{
                    book.setDoubanScore(Integer.parseInt(data));
                }catch (Exception e){
                    book.setPages(null);
                }
                break;
        }
    }

    public static Book initData(){
        Book book = new Book();
        book.setIsbn(null);
        book.setName(null);
        book.setSubname(null);
        book.setPhotoUrl(null);
        book.setDescription(null);
        book.setAuthor(null);
        book.setAuthorIntro(null);
        book.setTranslator(null);
        book.setPublishing(null);
        book.setPublished(null);
        book.setPages(null);
        book.setDoubanScore(null);
        return book;
    }



}
