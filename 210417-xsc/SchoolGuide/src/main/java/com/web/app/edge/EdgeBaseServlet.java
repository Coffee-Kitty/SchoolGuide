package com.web.app.edge;

import com.pojo.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/*
边的 servlet基类  用于各种方法的分配
 */

public class EdgeBaseServlet extends HttpServlet {
    //根据请求的最后一段路径来进行方法分发

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //  检查session中是否有user对象，如果有则放行
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return;//直接return掉 后端无响应然后重新登录
        }
        //1. 获取请求路径  例如/brand-case/brand/selectAll
        String uri = req.getRequestURI();

        // 获取最后一段路径，方法名
        int index = uri.lastIndexOf('/');
        String methodName = uri.substring(index + 1); //  获取到资源的二级路径  selectAll

        //2. 执行方法
        //2.1 获取Servlet /UserServlet 字节码对象 Class
        Class<? extends EdgeBaseServlet> cls = this.getClass();
        //2.2 获取方法 Method对象
        try {
            Method method = cls.getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
            //2.3 执行方法
            method.invoke(this, req, resp);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }


    }
}
