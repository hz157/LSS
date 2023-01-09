package com.itheima.lss.currency.interceptor;


import com.alibaba.fastjson.JSONObject;
import com.itheima.lss.currency.service.UserService;
import com.itheima.lss.currency.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Map;

public class JwtInterceptor implements HandlerInterceptor {
    Logger logger = LoggerFactory.getLogger(JwtInterceptor.class);

    @Resource
    UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod))
            return true;

        //从 http 请求头中取出 token
        String token = request.getHeader("token");
        logger.info("Request: " + token);

        if (token == null) {
//            throw new RuntimeException("无 token ，请重新登陆");
            //设置response状态
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            //返回的数据
            JSONObject res = new JSONObject();
            res.put("status","400");
            res.put("msg","Token does not exist");
            PrintWriter out = null ;
            out = response.getWriter();
            out.write(res.toString());
            out.flush();
            out.close();
            return false;
        }

        //验证 token
        if(!JwtUtil.checkSign(token)){
            JSONObject res = new JSONObject();
            res.put("status","400");
            res.put("msg","Invalid token");
            PrintWriter out = null ;
            out = response.getWriter();
            out.write(res.toString());
            out.flush();
            out.close();
        }

        //验证通过后， 这里测试取出JWT中存放的数据
        //获取 token 中的 userId
        String userId = JwtUtil.getUserId(token);
        logger.info("id : " + userId);

        //获取 token 中的其他数据
        Map<String, Object> info = JwtUtil.getInfo(token);
        info.forEach((k, v) -> System.out.println(k + ":" + v));
        request.setAttribute("user",userService.findById(info.get("id").toString()));
        return true;
    }

}


