package com.service;


import com.pojo.ALGraph;
import com.pojo.MGraph;
import com.pojo.Vertex;

import java.util.ArrayList;
import java.util.List;


/*
  以视图的形式得到数据然后赋予ALGraph

 */
public interface GraphService {

    /*
 查询构建一个ALGraph
  */
    ALGraph selectALGraph();

    /*
    转化为一个MGraph
     */
    MGraph selectMGraph(ALGraph alGraph);

    /*
    单源最短路劲 dijkstra  返回所有路径
     */
    List<ArrayList<Vertex>> singleShortestPathAll(ALGraph alGraph, int sourceIndex);

    /*
    多源最短路径  由floyd算法支持  同样返回所有路径
     */
    void mutipleShortestPath(MGraph mGraph, int[][] path, int[][] D);

    //打印路径的方法
    List<Vertex> printFloyd(MGraph mGraph, int i, int j, int[][] path);
}
