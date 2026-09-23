package com.pojo;


import java.util.ArrayList;
import java.util.List;

public class DrawGraph {

    //定点数和边数
    private int edgeNum;
    private int vertexNum;

    private List<Edge> edgeList;
    private List<Vertex> vertexList;
    //邻接矩阵
    private ArrayList<ArrayList<Boolean>> path;

    private Integer kind = -1;

    public DrawGraph() {
    }

    public DrawGraph(List<Edge> edgeList, List<Vertex> vertexList, ArrayList<ArrayList<Boolean>> path) {
        this.edgeList = edgeList;
        this.vertexList = vertexList;
        this.path = path;
        this.edgeNum = edgeList.size();
        this.vertexNum = vertexList.size();
    }

    public List<Edge> getEdgeList() {
        return edgeList;
    }

    public void setEdgeList(List<Edge> edgeList) {
        this.edgeList = edgeList;
    }

    public List<Vertex> getVertexList() {
        return vertexList;
    }

    public void setVertexList(List<Vertex> vertexList) {
        this.vertexList = vertexList;
    }

    public ArrayList<ArrayList<Boolean>> getPath() {
        return path;
    }

    public void setPath(ArrayList<ArrayList<Boolean>> path) {
        this.path = path;
    }

    public int getEdgeNum() {
        return edgeNum;
    }

    public void setEdgeNum(int edgeNum) {
        this.edgeNum = edgeNum;
    }

    public int getVertexNum() {
        return vertexNum;
    }

    public void setVertexNum(int vertexNum) {
        this.vertexNum = vertexNum;
    }

    public Integer getKind() {
        return kind;
    }

    public void setKind(Integer kind) {
        this.kind = kind;
    }

    @Override
    public String toString() {
        return "Graph{" +
                "edgeNum=" + edgeNum +
                ", vertexNum=" + vertexNum +
                ", edgeList=" + edgeList +
                ", vertexList=" + vertexList +
                ", path=" + path +
                ", kind=" + kind +
                '}';
    }
}
