package com.web.app.vertex;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.pojo.PageBean;
import com.pojo.Vertex;
import com.service.VertexService;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;


/*
提供 vertex 所有servlet方法
 */

@WebServlet("/VertexServlet/*")
public class VertexServlet extends VertexBaseServlet {

    private  static VertexService vertexService = new VertexService();

//    //查询所有
//    public void selectAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        List<Vertex> vertexList = vertexService.selectAll();
//        String jsonString = JSONObject.toJSONString(vertexList);
//        System.out.println(jsonString);
//
//        response.setContentType("text/json;charset=gbk"); //告知浏览器响应的数据是什么， 告知浏览器使用什么字符集进行解码
//        response.getWriter().write(jsonString);
//    }

    //根据id或mark查询
    public void selectByMarkOrName(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1. 接收数据
        BufferedReader br = req.getReader();
        String params = br.readLine();//json字符串
        JSONObject jsonObject = JSON.parseObject(params);
        String type = jsonObject.getString("type");
        //System.out.println(vertex);
        //System.out.println(type);
        //2. 调用service查询
        String vertex =jsonObject.getString("vertex");
        boolean flag = false;
        ArrayList<Vertex> vertices = new ArrayList<Vertex>();
        if ("name".equals(type)) {

            vertex = new String(vertex.getBytes("ISO-8859-1"), "UTF-8");
            System.out.println(vertex);
            vertices.add(vertexService.selectByName(vertex));
            flag = true;

        } else if ("mark".equals(type)) {
            vertices.add(vertexService.selectByMark(Integer.parseInt(vertex)));
            flag = true;
        }

        if (flag) {
            resp.setContentType("text/json;charset=utf-8");
            resp.getWriter().write(JSON.toJSONString(vertices));
        } else {
            resp.getWriter().write("failure");
        }

    }

    //增加一个景点
    public void addVertex(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1. 接收品牌数据
        BufferedReader br = req.getReader();
        String data = br.readLine();//json字符串
        //System.out.println(data);// {"id":"0","name":"æµè¯5225","mark":"15","data":"æµè¯333333","left":"50","top":"60"}
        String params = new String(data.getBytes("ISO-8859-1"), "UTF-8");
        //System.out.println(params);//        {"id":"0","name":"测试5225","mark":"15","data":"测试333333","left":"50","top":"60"}
        //转为Vertex对象
        Vertex vertex = JSON.parseObject(params, Vertex.class);
        //System.out.println(vertex);//Vertex{id=0, name='Ceshi', mark=16, data='da', left=500, top=1000}
        //2. 调用service添加
        boolean flag = vertexService.add(vertex);
        //3. 响应成功的标识
        if (flag) {
            resp.getWriter().write("success");
        } else {
            resp.getWriter().write("failure");
        }
    }

    //批量删除
    public void deleteByIds(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1. 接收数据 json  [1,2,3]
        BufferedReader br = request.getReader();
        String params = br.readLine();//json字符串
        //转为 int[]
        int[] ids = JSON.parseObject(params, int[].class);
        //2. 调用service添加
        vertexService.deleteByIds(ids);
        //3. 响应成功的标识
        response.getWriter().write("success");
    }

    //分页工具栏  根据当前页码和每页展示数查询
    public void selectByPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1. 接收 当前页码 和 每页展示条数    url?currentPage=1&pageSize=5
        String _currentPage = request.getParameter("currentPage");
        String _pageSize = request.getParameter("pageSize");

        int currentPage = Integer.parseInt(_currentPage);
        int pageSize = Integer.parseInt(_pageSize);

        //2. 调用service查询
        PageBean<Vertex> pageBean = vertexService.selectByPage(currentPage, pageSize);

        //2. 转为JSON
        String jsonString = JSON.toJSONString(pageBean);
        //3. 写数据
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(jsonString);
    }

    //修改
    public void modifyVertex(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1. 接收数据
        BufferedReader br = req.getReader();
        String data = br.readLine();//json字符串
        String params = new String(data.getBytes("ISO-8859-1"), "UTF-8");
        Vertex vertex = JSON.parseObject(params, Vertex.class);
        System.out.println(vertex);
        //2. 调用service
        boolean flag = false;
        flag = vertexService.update(vertex);
        if (flag) {
            resp.getWriter().write("success");
        } else {
            resp.getWriter().write("failure");
        }

    }


    //接收上传文件
    public void acceptTxt(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        //接收数据
        resp.setCharacterEncoding("utf-8");
        ServletInputStream inputStream = req.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line = null;
        ArrayList<Vertex> arrayList = new ArrayList<>();
        while ((line = reader.readLine()) != null) {
            //处理每行
           // System.out.println(line);
            if(line.equals("格式:唯一标识mark>地点名称>地点相关描述信息>以地图左上顶点为坐标原点，水平向右为x轴正向，竖直向下为y轴正向x坐标>y坐标")){
                continue;
            }
            String[] strings = line.split(">");//
            if(strings.length!=5){
//                resp.getWriter().write("格式不符");
//                return;
                // 不能简单认为length就是5  因为还有文件分割符------WebKitFormBoundaryezWhQcepB6lFTSDi
                continue;//所以也continue 忽略吧
            }
            int mark=Integer.parseInt(strings[0]);
            String name=strings[1];
            String data=strings[2];
            int left=Integer.parseInt(strings[3]);
            int top=Integer.parseInt(strings[4]);
            Vertex vertex = new Vertex(0,name,mark,data,left,top);
            System.out.println(vertex);
            arrayList.add(vertex);
        }

        boolean flag = vertexService.add(arrayList);//调用service持久化添加到数据库
        if(!flag) {
            resp.getWriter().write("上传信息有误，错误信息" );
            return;
        }

        resp.getWriter().write("添加成功，但请注意不符合格式的行并未被添加");

    }
}