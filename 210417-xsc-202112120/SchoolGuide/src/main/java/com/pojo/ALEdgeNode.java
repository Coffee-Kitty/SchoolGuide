package com.pojo;

//邻接表的
//边表节点类
public class ALEdgeNode {
    private int index; //弧尾指向的在顶点表的下标  0 1 2 3
    private Edge edge; //此边相关信息  权重等
    private ALEdgeNode next; //下一个邻接点

    public ALEdgeNode() {
    }

    public ALEdgeNode(int index, Edge edge, ALEdgeNode next) {
        this.index = index;
        this.edge = edge;
        this.next = next;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Edge getEdge() {
        return edge;
    }

    public void setEdge(Edge edge) {
        this.edge = edge;
    }

    public ALEdgeNode getNext() {
        return next;
    }

    public void setNext(ALEdgeNode next) {
        this.next = next;
    }

    @Override
    public String toString() {
        return "EdgeNode{" +
                "index=" + index +
                ", edge=" + edge +
                ", next=" + next +
                '}';
    }
}
