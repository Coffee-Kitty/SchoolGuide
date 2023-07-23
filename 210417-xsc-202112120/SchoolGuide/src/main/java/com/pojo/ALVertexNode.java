package com.pojo;

//邻接表的
//顶点表节点
public class ALVertexNode {
    private Vertex vertex;//顶点
    private ALEdgeNode edgeNode;//边表头指针

    public ALVertexNode() {
    }

    public ALVertexNode(Vertex vertex, ALEdgeNode edgeNode) {
        this.vertex = vertex;
        this.edgeNode = edgeNode;
    }

    public Vertex getVertex() {
        return vertex;
    }

    public void setVertex(Vertex vertex) {
        this.vertex = vertex;
    }

    public ALEdgeNode getEdgeNode() {
        return edgeNode;
    }

    public void setEdgeNode(ALEdgeNode edgeNode) {
        this.edgeNode = edgeNode;
    }

    @Override
    public String toString() {
        return "VertexNode{" +
                "vertex=" + vertex +
                ", edgeNode=" + edgeNode +
                '}';
    }
}
