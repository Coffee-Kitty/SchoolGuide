package com.pojo;


public abstract class AbstractGraph {
    //有权无向图
    public final static int UNDIRECTED_WEIGHTED_GRAPH = 1;


    private int vertexNum;//顶点数
    private int edgeNum;//边数
    private int kind;//种类

    public AbstractGraph() {
    }

    public int getVertexNum() {
        return vertexNum;
    }

    public void setVertexNum(int vertexNum) {
        this.vertexNum = vertexNum;
    }

    public int getEdgeNum() {
        return edgeNum;
    }

    public void setEdgeNum(int edgeNum) {
        this.edgeNum = edgeNum;
    }

    public int getKind() {
        return kind;
    }

    public void setKind(int kind) {
        this.kind = kind;
    }

    @Override
    public String toString() {
        return "AbstractGraph{" +
                "vertexNum=" + vertexNum +
                ", edgeNum=" + edgeNum +
                ", kind=" + kind +
                '}';
    }
}
