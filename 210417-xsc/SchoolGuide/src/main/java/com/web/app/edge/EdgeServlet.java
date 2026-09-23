package com.web.app.edge;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.pojo.Edge;
import com.pojo.PageBean;
import com.service.EdgeService;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;


/*
提供 edge 所有servlet方法
 */

@WebServlet("/EdgeServlet/*")
public class EdgeServlet extends EdgeBaseServlet {


    private static EdgeService edgeService = new EdgeService();

//    //查询所有
////    public void selectAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
////        List<Edge> edges = edgeService.selectAll();
////        String jsonString = JSONObject.toJSONString(edges);
////
////
////        response.setContentType("text/json;charset=UTF-8"); //告知浏览器响应的数据是什么， 告知浏览器使用什么字符集进行解码
////        response.getWriter().write(jsonString);
////    }

    //根据  mark1  mark2查询
    public void selectByMarks(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //1. 接收数据
        BufferedReader br = req.getReader();
        String params = br.readLine();//json字符串

        boolean flag = false;
        ArrayList<Edge> edges = new ArrayList<Edge>();
        if (params != null) {
            JSONObject jsonObject = JSON.parseObject(params);
            String mark1 = jsonObject.getString("mark1");
            String mark2 = jsonObject.getString("mark2");
            //2. 调用service查询
            edges.add(edgeService.selectByMarks(Integer.parseInt(mark1), Integer.parseInt(mark2)));
            flag = true;
        }

        if (flag) {
            resp.setContentType("text/json;charset=utf-8");
            resp.getWriter().write(JSON.toJSONString(edges));
        } else {
            resp.getWriter().write("failure");
        }

    }

    //增加一条边
    public void addEdge(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1. 接收数据
        BufferedReader br = req.getReader();
        String data = br.readLine();//json字符串
        //2. 调用service添加
        boolean flag = false;
        if (data != null) {
            String params = new String(data.getBytes("ISO-8859-1"), "UTF-8");
            //转为对象
            Edge edge = JSON.parseObject(params, Edge.class);
            flag = edgeService.add(edge);
        }
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
        boolean flag = false;
        //2. 调用service添加
        if (params != null) {
            //转为 int[]
            int[] ids = JSON.parseObject(params, int[].class);
            edgeService.deleteByIds(ids);
            flag = true;
        }
        //3. 响应成功的标识
        if (flag) {
            response.getWriter().write("success");
        } else {
            response.getWriter().write("failure");
        }

    }

    //分页工具栏  根据当前页码和每页展示数查询
    public void selectByPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1. 接收 当前页码 和 每页展示条数    url?currentPage=1&pageSize=5
        String _currentPage = request.getParameter("currentPage");
        String _pageSize = request.getParameter("pageSize");

        int currentPage = Integer.parseInt(_currentPage);
        int pageSize = Integer.parseInt(_pageSize);

        //2. 调用service查询
        PageBean<Edge> pageBean = edgeService.selectByPage(currentPage, pageSize);

        //2. 转为JSON
        String jsonString = JSON.toJSONString(pageBean);

        //3. 写数据
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(jsonString);
    }

    //修改
    public void modifyEdge(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1. 接收数据
        BufferedReader br = req.getReader();
        String data = br.readLine();//json字符串

        boolean flag = false;
        //2. 调用service
        if (data != null) {
            String params = new String(data.getBytes("ISO-8859-1"), "UTF-8");
            Edge egde = JSON.parseObject(params, Edge.class);
            flag = edgeService.update(egde);
        }
        // 返回数据
        if (flag) {
            resp.getWriter().write("success");
        } else {
            resp.getWriter().write("failure");
        }

    }


    //接收上传文件
    public void acceptTxt(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
       resp.setCharacterEncoding("utf-8");
        //接收数据
        ServletInputStream inputStream = req.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line = null;
        ArrayList<Edge> edgeArrayList = new ArrayList<>();
        while ((line = reader.readLine()) != null) {
            //处理每行
            System.out.println(line);
            if(line.equals("格式:边邻接点1>邻接点2>距离>一般步行时间>一般骑行时间")){
                continue;
            }
            String[] strings = line.split(">");//0>4>200m>10min>5min
            System.out.println(strings.length);
            if(strings.length!=5){
//                resp.getWriter().write("格式不符");
//                return;
               // 不能简单认为length就是5  因为还有文件分割符------WebKitFormBoundaryezWhQcepB6lFTSDi
                continue;//所以也continue 忽略吧
            }
            int mark1=Integer.parseInt(strings[0]);
            int mark2=Integer.parseInt(strings[1]);
            String distance=strings[2];
            String walkTime=strings[3];
            String rideTime=strings[4];
            Edge edge = new Edge(0, mark1, mark2, distance, walkTime, rideTime);
            edgeArrayList.add(edge);
        }
        boolean flag = edgeService.add(edgeArrayList);//调用service持久化添加到数据库

        if(!flag){
            resp.getWriter().write("上传信息有误，错误信息：");
            return;
        }
        resp.getWriter().write("添加成功，但请注意不符合格式的行并未被添加");

    }
}