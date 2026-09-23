package 弃用;

import com.alibaba.fastjson.JSONObject;
import com.pojo.Vertex;
import com.service.VertexService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/*
返回景点所有信息给前端  json格式
 */
@WebServlet("/VertexDetailServlet")
public class VertexDetailServlet extends HttpServlet {
    private VertexService vertexService = new VertexService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Vertex> vertexList = vertexService.selectAll();
        String jsonString = JSONObject.toJSONString(vertexList);


        response.setContentType("text/json;charset=gbk"); //告知浏览器响应的数据是什么， 告知浏览器使用什么字符集进行解码
        response.getWriter().write(jsonString);
    }
}
