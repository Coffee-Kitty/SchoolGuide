//package 弃用;
//
//import com.alibaba.fastjson.JSONObject;
//import com.pojo.Graph;
//import 弃用.GraphAllService;
//import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
//
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//@WebServlet("/GraphServlet")
//public class GraphServlet extends HttpServlet {
//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        //查询
//        GraphAllService graphAllService = new GraphAllService();
//        Graph graph = graphAllService.getGraph();
//        //转json
//        String jsonString = JSONObject.toJSONString(graph);
//        // 数据
//        resp.setContentType("text/json;charset=utf-8"); //告知浏览器响应的数据是什么， 告知浏览器使用什么字符集进行解码
//        resp.getWriter().write(jsonString);
//
//        Object user = req.getAttribute("user");
//        System.out.println(user);
//
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        super.doGet(req, resp);
//    }
//}
