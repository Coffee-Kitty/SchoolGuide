package com.web.app.register;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.pojo.User;
import com.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
/*
1. 用户在注册页面输入用户名和密码，提交请求给RegisterServlet
2. 在RegisterServlet中接收请求和数据[用户名和密码]
3. 调用UserService提供的服务查询数据库表  查询的结果封装到User对象中进行返回
4. 在RegisterServlet中判断返回的User对象是否为null
        如果为nul，说明根据用户名可用，则调用相应来实现添加用户
        如果不为null,则说明用户不可以，返回"用户名已存在"数据给前端
 */

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.接收请求和数据[用户名和密码]
        BufferedReader br = request.getReader();
        String params = br.readLine();//json字符串
        JSONObject jsonObject = JSON.parseObject(params);
        String username = jsonObject.getString("username");
        String password = jsonObject.getString("password");
        String phonenumber = jsonObject.getString("phonenumber");

        // 获取用户输入的验证码
        String checkCode = jsonObject.getString("checkCode");

        // 程序生成的验证码，从Session获取
        HttpSession session = request.getSession();
        String checkCodeGen = (String) session.getAttribute("checkCodeGen");
//        System.out.println(checkCode);
//        System.out.println(checkCodeGen);
        // 比对
        if (checkCode == null || checkCode.equals("") || !checkCodeGen.equalsIgnoreCase(checkCode)) {

            String jsonString = JSON.toJSONString(new String("{\"register_msg\":\"验证码错误\"}"));
            response.setContentType("text/json;charset=utf-8");
            response.getWriter().write(jsonString);
            // 不允许注册
            return;
        }

        //封装用户对象
        User user = new User();
        user.setUsername(username);
        user.setPasswd(password);
        user.setPhonenumber(phonenumber);

        //2. 调用UserService提供的服务查询数据库表
        UserService userService = new UserService();
        User u = userService.selectByUsername(username);

        //3.在RegisterServlet中判断返回的User对象是否为nul
        if (u == null && !username.equals("")) {
            // 用户不存在可以被添加
            userService.addUser(user);
            //注册功能，跳转登陆页面
            response.setContentType("text/json;charset=utf-8");
            response.getWriter().write(JSON.toJSONString(new String("{\"register_msg\":\"注册成功，请登录\"}")));
        } else {
            // 用户名存在，给出提示信息
            //注册失败，跳转到注册页面
            response.setContentType("text/json;charset=utf-8");
            response.getWriter().write(JSON.toJSONString(new String("{\"register_msg\":\"用户名已存在\"}")));
        }

    }
}
