package 弃用;

import com.alibaba.fastjson.JSON;
import com.pojo.Vertex;
import com.service.VertexService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;


@WebServlet("/VertexAddServlet")
public class VertexAddServlet extends HttpServlet {
    private VertexService vertexService = new VertexService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1. 接收品牌数据
        BufferedReader br = req.getReader();
        String params = br.readLine();//json字符串
        System.out.println(params);//{"id":"0","name":"Ceshi","mark":"16","data":"da","left":"500","top":"1000"}
            //转为Vertex对象
            Vertex vertex = JSON.parseObject(params, Vertex.class);
            System.out.println(vertex);//Vertex{id=0, name='Ceshi', mark=16, data='da', left=500, top=1000}
            //2. 调用service添加
            boolean flag = vertexService.add(vertex);
            //3. 响应成功的标识
            if (flag) {
                resp.getWriter().write("success");
        } else {
            resp.getWriter().write("failure");
        }
    }
}
/*
后台服务器[Tomcat]会对HTTP请求中的数据进行解析并把解析结果存入到一个对象中
所存入的对象即为request对象，所以我们可以从request对象中获取请求的相关参数

业务处理完后，后台就需要给前端返回业务处理的结果即响应数据
把响应数据封装到response对象中
后台服务器[Tomcat]会解析response对象,按照[响应行+响应头+响应体]格式拼接结果
 */