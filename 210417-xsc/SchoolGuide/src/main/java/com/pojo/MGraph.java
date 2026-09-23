package com.pojo;


import java.util.ArrayList;
import java.util.List;
/*
图的邻接矩阵表示
 */

public class MGraph extends AbstractGraph {

    //顶点数组
    private ArrayList<Vertex> vertexArrayList;
    //邻接矩阵
    private List<ArrayList<Integer>> vertexMatrix = null;

    public MGraph(List<ArrayList<Integer>> vertexMatrix) {
        this.vertexMatrix = vertexMatrix;
    }

    public MGraph() {
    }

    //提供一个通过邻接表转化为邻接矩阵的方法
    public static MGraph ToMGraph(AbstractGraph abstractGraph) {
        if (abstractGraph == null) return null;

        MGraph graph = new MGraph();
        ALGraph alGraph = (ALGraph) abstractGraph;
        //设置边数 节点数  种类
        graph.setEdgeNum(alGraph.getEdgeNum());
        graph.setKind(alGraph.getKind());
        graph.setVertexNum(alGraph.getVertexNum());
        //构造邻接矩阵
        List<ArrayList<Integer>> matrix = new ArrayList<>();
        for (int i = 0; i < graph.getVertexNum(); i++) {
            ArrayList<Integer> arrayList = new ArrayList<>();
            for (int j = 0; j < graph.getVertexNum(); j++) {
                if (i != j) {
                    arrayList.add(Integer.MAX_VALUE / 2);
                } else {
                    arrayList.add(0);
                }

            }
            matrix.add(arrayList);
        }
        graph.setVertexMatrix(matrix);
        graph.setVertexArrayList(new ArrayList<Vertex>());
        //接着根据邻接表去初始化邻接矩阵   并且顶点表
        ArrayList<ALVertexNode> vertexList = alGraph.getVertexList();
        for (int i = 0; i < vertexList.size(); i++) {
            ALVertexNode vertexNode = vertexList.get(i);
            graph.getVertexArrayList().add(vertexNode.getVertex());
            if (vertexNode.getEdgeNode() == null) continue;
            ArrayList<Integer> mGraphList = graph.getVertexMatrix().get(i);
            for (ALEdgeNode p = vertexNode.getEdgeNode(); p != null; p = p.getNext()) {
                int index = p.getIndex();
                Edge edge = p.getEdge();
                mGraphList.set(index, edge.getWeightByDistance());
            }

        }
        return graph;


    }

    public void setVertexMatrix(ArrayList<Vertex> vertexArrayList, List<ArrayList<Integer>> vertexMatrix) {
        this.vertexArrayList = vertexArrayList;
        this.vertexMatrix = vertexMatrix;
    }

    public List<ArrayList<Integer>> getVertexMatrix() {
        return vertexMatrix;
    }

    public void setVertexMatrix(List<ArrayList<Integer>> vertexMatrix) {
        this.vertexMatrix = vertexMatrix;
    }

    public ArrayList<Vertex> getVertexArrayList() {
        return vertexArrayList;
    }

    public void setVertexArrayList(ArrayList<Vertex> vertexArrayList) {
        this.vertexArrayList = vertexArrayList;
    }

    @Override
    public String toString() {
        return "MGraph{" +
                "vertexMatrix=" + vertexMatrix +
                '}';
    }

    //提供一个根据index返回在 顶点数组中顶点的方法
    public Vertex getVertexByIndex(int index) {
        return vertexArrayList.get(index);
    }
}
