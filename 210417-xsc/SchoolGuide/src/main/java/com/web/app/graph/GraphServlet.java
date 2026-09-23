package com.web.app.graph;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.pojo.ALGraph;
import com.pojo.DrawGraph;
import com.pojo.MGraph;
import com.pojo.Vertex;
import com.service.GraphService;
import com.service.Impl.GraphServiceImpl;
import com.web.app.edge.EdgeBaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/*
提供 edge 所有servlet方法
 */

@WebServlet("/GraphServlet/*")
public class GraphServlet extends EdgeBaseServlet {

    private  ALGraph graph;
    private  MGraph mGraph;

    private  int[][] path;
    private   int[][] D;


    private  GraphService graphService;

    public GraphServlet() {
        graphService = new GraphServiceImpl();
        graph = graphService.selectALGraph();
        mGraph=MGraph.ToMGraph(graph);
        int vertexNum = mGraph.getVertexNum();
        path = new int[vertexNum][vertexNum];
        D = new int[vertexNum][vertexNum];

        graphService.mutipleShortestPath(mGraph, path, D);

//        for(int i=0;i<vertexNum;i++){
//            for(int j=0;j<vertexNum;j++){
//                System.out.print(i+" "+j+" "+path[i][j]+"\t");
//            }
//            System.out.println();
//        }
//        System.out.println("-------------------");
//        for(int i=0;i<vertexNum;i++){
//            for(int j=0;j<vertexNum;j++){
//                System.out.print(i+" "+j+" "+D[i][j]+"\t");
//            }
//            System.out.println();
//        }
    }


    /*
    返回一个邻接表图
     */
    public void getALGraph(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/json;charset=utf-8");
        //解析json对象出现$ref: "$.list[0]"的解决办法     SerializerFeature.DisableCircularReferenceDetect避免循环引用
        response.getWriter().write(JSONObject.toJSONString(graph, SerializerFeature.DisableCircularReferenceDetect));
    }

    /*
    单源最短路径  直接返回所有路径
     */
    public void singleShortestPath(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //尤其注意接收的时json串
        BufferedReader br = request.getReader();
        String params = br.readLine();//json字符串
        JSONObject jsonObject = JSON.parseObject(params);
        String source = jsonObject.getString("source");
        String type = jsonObject.getString("type");
        if (source == null || source.equals("") || type == null || type.equals("")) {
            return;
        }
        System.out.println(source+type);
        int sourceIndex = -1;
        if ("name".equals(type)) {
            source = new String(source.getBytes("ISO-8859-1"), "UTF-8");
            sourceIndex = graph.getVertexIndex(source);
        } else {
            sourceIndex = graph.getVertexIndex(Integer.parseInt(source));
        }
        if (sourceIndex == -1) {
            return;
        }
        List<ArrayList<Vertex>> singleVertexListAll = graphService.singleShortestPathAll(graph, sourceIndex);
        if (singleVertexListAll != null) {
            response.setContentType("text/json;charset=utf-8");
            response.getWriter().write(JSONObject.toJSONString(singleVertexListAll, SerializerFeature.DisableCircularReferenceDetect));
        } else {
            response.getWriter().write("failure");
        }
        System.out.println("成功");
    }

    /*
    多源最短路径
     */
    public void mutipleShortestPath(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //尤其注意接收的时json串
        BufferedReader br = request.getReader();
        String params = br.readLine();//json字符串
        JSONObject jsonObject = JSON.parseObject(params);
        String source = jsonObject.getString("source");
        String target = jsonObject.getString("target");
        String type = jsonObject.getString("type");
      //  System.out.println(source+target+type);
        if (target == null || target.equals("") || source == null || source.equals("") || type == null || type.equals("")) {
           // System.out.println("失败");
            return;
        }
        int sourceIndex = -1, targetIndex = -1;
        if ("name".equals(type)) {
            source = new String(source.getBytes("ISO-8859-1"), "UTF-8");
            target = new String(target.getBytes("ISO-8859-1"), "UTF-8");
            sourceIndex = graph.getVertexIndex(source);
            targetIndex = graph.getVertexIndex(target);

        } else {
            sourceIndex = graph.getVertexIndex(Integer.parseInt(source));
            targetIndex = graph.getVertexIndex(Integer.parseInt(target));
        }
        if (sourceIndex == -1 || targetIndex == -1) {
           // System.out.println("失败");
            return;
        }

       // System.out.println(sourceIndex+targetIndex);
        List<Vertex> vertexList = graphService.printFloyd(mGraph, sourceIndex, targetIndex, path);
       // System.out.println(vertexList);
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(JSONObject.toJSONString(vertexList));

    }


    public void drawAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //查询
        GraphServiceImpl graphServiceIml = (GraphServiceImpl) this.graphService;
        DrawGraph drawGraph = graphServiceIml.selectDrawGraph();
        //转json
        String jsonString = JSONObject.toJSONString(drawGraph);
        // 数据
        response.setContentType("text/json;charset=utf-8"); //告知浏览器响应的数据是什么， 告知浏览器使用什么字符集进行解码
        response.getWriter().write(jsonString);
    }

}