package 弃用;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.pojo.Vertex;
import com.service.VertexService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

@WebServlet("/VertexSelectServlet")
public class VertexSelectServlet extends HttpServlet {
    private VertexService vertexService = new VertexService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1. 接收数据
        BufferedReader br = req.getReader();
        String params = br.readLine();//json字符串
        JSONObject jsonObject = JSON.parseObject(params);
        String vertex = jsonObject.getString("vertex");
        String type = jsonObject.getString("type");
        System.out.println(vertex);
        System.out.println(type);
        //2. 调用service查询
        boolean flag = false;
        Vertex res = null;
        if ("name".equals(type)) {
            res = vertexService.selectByName(vertex);
            flag = true;

        } else if ("mark".equals(type)) {
            res = vertexService.selectByMark(Integer.parseInt(vertex));
            flag = true;
        }

        //3. 响应成功的标识
//        HashMap<String, Object> map = new HashMap<String, Object>();
//        if(flag){
//            resp.setContentType("text/json;charset=gbk");
//            map.put("success",true);
//            map.put("data",res);
//        }else{
//            map.put("success",false);
//        }
//        resp.getWriter().write(JSON.toJSONString(map));
        if (flag) {
            resp.setContentType("text/json;charset=gbk");
            resp.getWriter().write(JSON.toJSONString(res));
        } else {
            resp.getWriter().write("failure");
        }

    }
}
