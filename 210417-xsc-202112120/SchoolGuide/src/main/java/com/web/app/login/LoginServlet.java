package com.web.app.login;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.pojo.User;
import com.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;


/*
(1)前端通过表单发送请求和数据给Web层的LoginServlet
(2)在LoginServlet中接收请求和数据[用户名和密码]
(3)LoginServlet接收到请求和数据后，调用Service层完成根据用户名和密码查询用户对象
(4)在Service层需要编写UserService类，在类中实现login方法，方法中调用Dao层的
UserMapper
(5)在UserMapper接口中，声明一个根据用户名和密码查询用户信息的方法
(6)Dao层把数据查询出来以后，将返回数据封装到User对象，将对象交给Service层
(7)Service层将数据返回给Web层
(8)Web层获取到User对象后，判断User对象，如果为Null,则将错误信息响应给登录页面，如果不
为Null，则跳转到列表页面，并把当前登录用户的信息存入Session携带到列表页面
 */

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private static UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/json;charset=utf8");

        //1. 接收用户名和密码
        BufferedReader br = req.getReader();
        String params = br.readLine();//json字符串

        HashMap<String, String> failure = new HashMap<String, String>();
        failure.put("login_msg", "用户名或密码错误");
        if (params == null) {
//            req.setAttribute("login_msg","用户名或密码错误");
            resp.getWriter().write(JSON.toJSONString(failure));
            return;
        }
        // System.out.println(params);
        JSONObject jsonObject = JSON.parseObject(params);
        String username = jsonObject.getString("username");
        String password = jsonObject.getString("password");
        //获取复选框数据
        String remember = jsonObject.getString("remember");

        //2.调用UserService服务查询
        User user = userService.selectByUsernameAndPasswd(username, password);

        //3. 判断
        if (user != null) {
            //登录成功，跳转到GraphServlet

            //判断用户是否勾选记住我，字符串写前面是为了避免出现空指针异常
            if (remember.equals("true")) {
                //勾选了，发送Cookie
                //1. 创建Cookie对象
                Cookie c_username = new Cookie("username", username);
                Cookie c_password = new Cookie("password", password);
                // 设置Cookie的存活时间
                c_username.setMaxAge(60 * 60 * 24 * 7);
                c_password.setMaxAge(60 * 60 * 24 * 7);
                //2. 发送
                resp.addCookie(c_username);
                resp.addCookie(c_password);
            }

            //将登陆成功后的user对象，存储到session
            HttpSession session = req.getSession(true);
            session.setAttribute("user", user);
            HashMap<String, String> success = new HashMap<String, String>();
            success.put("login_msg", "登录成功");
            resp.getWriter().write(JSON.toJSONString(success));
//            req.setAttribute("login_msg","用户名或密码错误");

        } else {
            // 登录失败,
            resp.getWriter().write(JSON.toJSONString(failure));
//            req.setAttribute("login_msg","用户名或密码错误");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }
}
