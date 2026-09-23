package com.pojo;

import java.util.ArrayList;

/*
图的邻接表表示
 */

public class ALGraph extends AbstractGraph {

    //顶点表
    private ArrayList<ALVertexNode> VertexList;

    public ALGraph() {
    }

    public ALGraph(ArrayList<ALVertexNode> vertexList, int kind) {
        VertexList = vertexList;
        this.setKind(kind);
    }

    public ArrayList<ALVertexNode> getVertexList() {
        return VertexList;
    }

    public void setVertexList(ArrayList<ALVertexNode> vertexList) {
        VertexList = vertexList;
    }

    @Override
    public String toString() {
        return "ALGraph{" +
                "VertexList=" + VertexList +
                '}';
    }

    //获得某顶点的在顶点表中的位置
    //不存在则返回-1
    public int getVertexIndex(Vertex vertex) {
        for (int i = 0; i < VertexList.size(); i++) {
            Vertex vertex1 = VertexList.get(i).getVertex();
            if (vertex1.equals(vertex)) {
                return i;
            }
        }
        return -1;
    }

    //根据景点名称来返回位置
    public int getVertexIndex(String name) {
        for (int i = 0; i < VertexList.size(); i++) {
            Vertex vertex1 = VertexList.get(i).getVertex();
            if (vertex1.getName().equals(name)) {
                return i;
            }
        }
        return -1;
    }

    //根据景点标号mark来返回位置
    public int getVertexIndex(int mark) {
        for (int i = 0; i < VertexList.size(); i++) {
            Vertex vertex1 = VertexList.get(i).getVertex();
//            if(vertex1==null)return -1;
            if (vertex1.getMark() == mark) {
                return i;
            }
        }
        return -1;
    }

}

/*
子类不能继承父类的私有属性，但如果子类中公有的方法影响到了父类的私有属性，那么私有属性是能够被子类使用的。
 */