//package 弃用.filter;
//
//import com.pojo.User;
//import com.service.UserService;
//
//import javax.servlet.*;
//import javax.servlet.annotation.WebFilter;
//import javax.servlet.http.Cookie;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//import java.io.IOException;
//
//@WebFilter("/*")
//public class LoginFilter implements Filter {
//    private UserService userService=new UserService();
//    public void init(FilterConfig filterConfig) throws ServletException {
//
//    }
//
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//
//         //放行前---------------------------------------------------------------
//
//        //判断访问资源路径是否和登录注册相关
//        //1,在数组中存储登陆和注册相关的资源路径
//        String[] urls = {"/index.html","/login.html","/register.html", "/imgs/", "/css/","/js/","/LoginServlet",  "/RegisterServlet", "/CheckCodeServlet"};
//        //2,获取当前访问的资源路径
//        HttpServletRequest req = (HttpServletRequest) request;
//        HttpServletResponse resp = (HttpServletResponse) response;
//        String url = req.getRequestURL().toString();
//        //3,遍历数组，获取到每一个需要放行的资源路径
//        boolean flag=false;
//        for (String u : urls) {
//            //4,判断当前访问的资源路径字符串是否包含要放行的的资源路径字符串
//                     /*
//                比如当前访问的资源路径是  /brand-demo/login2.html
//                而字符串 /brand-demo/login2.html 包含了  字符串 /login2.html ，所以这个字符串就需要放行
//            */
//            if (url.contains(u)) {
//                //找到了，放行
//                flag=true;
//            }
//        }
//        if(flag){
//            chain.doFilter(request, response);
//        }else {
//            resp.sendRedirect("/login.html");
//        }
//
//
////        //放行--------------------------------------------------------------------------
//        // 检查session中是否有user对象，如果有则放行
////        HttpSession session = req.getSession();
////        User user = (User) session.getAttribute("user");
////        if (user != null) {
////            chain.doFilter(request, response);
////
////        }
//
//        // 如果都没有成功，则跳转到登录页面
//        //resp.resetBuffer();
//       //resp.sendRedirect("/login.html"); //  Cannot call sendRedirect() after the response has been committed
//
//
//
//    //放行后-------------------------------------------------------------------------
//    }
//
//    public void destroy() {
//
//    }
//}
