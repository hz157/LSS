<<<<<<< HEAD
# Library Share System(LSS) 书籍共享系统
> Classwork at the end of the third year of college 2022-12-13 ~ 2022-12-20

[![JDK](https://img.shields.io/badge/JDK=19.0.1-orange.svg)](https://www.oracle.com/java/technologies/javase/jdk19-archive-downloads.html)
[![MybatisPlus](https://img.shields.io/badge/MybatisPlus=3.5.2-blue.svg)](https://baomidou.com/)
[![Issues](https://img.shields.io/github/issues/hz157/LSS)](https://github.com/hz157/LSS/issues)
[![Code size](https://img.shields.io/github/languages/code-size/hz157/LSS?color=blueviolet)](https://github.com/hz157/LSS)
[![Repo size](https://img.shields.io/github/repo-size/hz157/LSS?color=eb56fd)](https://github.com/hz157/LSS/main)
[![Last commit](https://img.shields.io/github/last-commit/hz157/LSS/main)](https://github.com/hz157/LSS/commits/main)

## Use tools
- Wechat Dev Tool
- IntelliJ IDEA
- phpMyAdmin

## Weapp UI
![](https://raw.githubusercontent.com/hz157/LSS/main/Image/20221216004131.png)
## Project Structure
![](https://raw.githubusercontent.com/hz157/LSS/main/Image/20230104190629.png)

## Server
>Project Branch Springboot -> [Branch-Springboot](https://github.com/hz157/LSS/tree/Springboot)

Use technology
<br>
 <img src="https://baomidou.com/img/logo.svg" width="25%">
 <img src="https://spring.io/images/spring-logo-2022-dark-2f10e8055653ec50e693eb444291d742.svg" width="50%">
 - [Springboot](https://spring.io/)
 - [MybatisPlus](https://baomidou.com/)

## Client
>Project Branch Weapp -> [Branch-Weapp](https://github.com/hz157/LSS/tree/Weapp)

Use technology
- [Weapp](https://developers.weixin.qq.com/miniprogram/dev/devtools/devtools.html)
- [iview Weapp UI](https://weapp.iviewui.com/)

## Introduce
This Project Introduce Doc -> [introduce.md](https://github.com/hz157/LSS/blob/main/Doc/introduce.md)

## Deployment
This Deployment Instructions Doc -> [deployment.md](https://github.com/hz157/LSS/blob/main/Doc/deployment.md)

## Developer
https://github.com/hz157
=======
# Library_Share_System

## API Document
链接: https://cymoekc5wx.apifox.cn  访问密码: 可用时公布

#### 存在问题
##### 身份鉴别
> 身份鉴别存在Token失效及续约问题未解决，
> 


## 部署需要修改的参数
- src/main/java/com/itheima/lss/currency/util/APIs/IsbnApi.java
  - private final String key 变量 
  >设置自己的请求Key
- src/main/resources/application.yml
  - spring: datasource: url 
  > 设置为自己的数据库连接
>>>>>>> fb34e3ecb1def20f3ba86ef2fb2fc21d890ee7fe
