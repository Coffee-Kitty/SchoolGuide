package com.service.Impl;

import com.mapper.ALGraphMapper;
import com.mapper.VertexMapper;
import com.pojo.*;
import com.service.EdgeService;
import com.service.GraphService;
import com.service.VertexService;
import com.utils.SqlSessionFactoryUtil;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;


public class GraphServiceImpl implements GraphService {
    private SqlSessionFactory sqlSessionFactory = SqlSessionFactoryUtil.getSqlSessionFactory();

    /*
    dijkstra算法  前置条件: 递增路径  权值无负值  连通图

    单源最短路径问题
    collected[i]  表示收集的顶点集合
    dist[v.index] 表示从源点source到顶点v的最短路径  v.index表示v在顶点表中的下标 恰巧等于mark
    v=path[w] 表示从源点s到顶点w的最短路径上w的前一个顶点是v  同样这里也使用index
     */

    /*
    查询构建ALGraph
     */
    public ALGraph selectALGraph() {
        //1.准备ALGraph及填充的顶点表
        ALGraph alGraph = new ALGraph();
        ArrayList<ALVertexNode> vertexList = new ArrayList<ALVertexNode>();

        //2.调用sql查询并赋值
        SqlSession sqlSession = sqlSessionFactory.openSession();
        ALGraphMapper mapper = sqlSession.getMapper(ALGraphMapper.class);
        int edgeNum = mapper.selectEdgeNum();
        int vertexNum = mapper.selectVertexNum();
        // 赋值 顶点数 和边数
        alGraph.setEdgeNum(edgeNum);
        alGraph.setVertexNum(vertexNum);
        VertexMapper vertexMapper = sqlSession.getMapper(VertexMapper.class);
        List<Vertex> vertexList1 = vertexMapper.selectAll();
        // 赋值顶点表
        for (int i = 0; i < vertexNum; i++) {
            //赋值每一个顶点
            Vertex vertex = vertexList1.get(i);
            ALVertexNode vertexNode = new ALVertexNode(vertex, null);
            vertexList.add(vertexNode);
        }

        //分开赋值边表 确保无权图
        for (int i = 0; i < vertexNum; i++) {
            List<Edge> edges = mapper.selectAdjEdgeByMark(vertexList.get(i).getVertex().getMark());

            //赋值 边表节点
            ALEdgeNode tail1 = null;
            ALEdgeNode tail2 = null;
            for (Edge edge : edges) {
                //准备节点
                ALVertexNode vertexNode1 = vertexList.get(i);
                int index = edge.getMark2();//顶点在顶点表中的位置下标  等于顶点的标号
                ALVertexNode vertexNode2 = vertexList.get(index);
                ALEdgeNode edgeNode1 = new ALEdgeNode(index, edge, null);
                ALEdgeNode edgeNode2 = new ALEdgeNode(i, edge, null);
                //进行尾插
                //判断头节点
                tail1 = vertexNode1.getEdgeNode();
                if (tail1 != null) {
                    while (tail1.getNext() != null) {
                        tail1 = tail1.getNext();
                    }
                    tail1.setNext(edgeNode1);
                } else {
                    vertexNode1.setEdgeNode(edgeNode1);
                }
                //！！！！！！！！！！！！！！！！！！！接着是无权图的另一侧邻接表插入
                tail2 = vertexNode2.getEdgeNode();
                if (tail2 != null) {
                    while (tail2.getNext() != null) {
                        tail2 = tail2.getNext();
                    }
                    tail2.setNext(edgeNode2);
                } else {
                    vertexNode2.setEdgeNode(edgeNode2);
                }
            }

        }
        alGraph.setVertexList(vertexList);
        alGraph.setKind(AbstractGraph.UNDIRECTED_WEIGHTED_GRAPH);
        sqlSession.close();
        return alGraph;

    }


    //子方法：获取未被收集的并且最小的dist[v.index]对应的v.index
    //如果不存在 则返回-1
    private int getDistMin(int[] dist, boolean[] collected) {
        int mindata = Integer.MAX_VALUE;
        int res = -1;
        for (int i = 0; i < dist.length; i++) {
            if (!collected[i] && dist[i] <= mindata) {//等于就需要收集  因为初始化dist[]时为 Integer.Max_Value
                mindata = dist[i];
                res = i;
            }
        }
        return res;
    }

    public void dijkstra(ALGraph alGraph, int[] dist, int[] path, boolean[] collected) {
        while (true) {
            //取出最小dist[v.index]的v.index
            //之后根据v.index更新其邻接点
            int i = getDistMin(dist, collected);
            //如果全部收集完  则dist一定已经最小
            if (i == -1) {
                break;
            }
            collected[i] = true;//收集 v

            ALEdgeNode firstEdgeNode = alGraph.getVertexList().get(i).getEdgeNode();
            for (ALEdgeNode p = firstEdgeNode; p != null; p = p.getNext()) {
                int pIndex = p.getIndex();
                int weight = p.getEdge().getWeightByDistance();
                if (!collected[pIndex]) {//对每个邻接点 如果未被收集则看是否可以更新
                    if (dist[i] + weight < dist[pIndex]) {
                        dist[pIndex] = Math.min(dist[pIndex], dist[i] + weight);//更新dist
                        path[pIndex] = i;//更新path
                    }
                    //更新后此处查看是否为负  避免负值圈
                    if (dist[pIndex] < 0) {
                        try {
                            throw new Exception("非递增路径");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

        }

    }

    //dijkstra支撑的查询source到所有点的最短路径
    //顺序返回所经过的顶点 s->t  同时传入
    public List<ArrayList<Vertex>> singleShortestPathAll(ALGraph alGraph, int sourceIndex) {
        if (sourceIndex == -1) return null;
        List<ArrayList<Vertex>> vertexListAll = new ArrayList<ArrayList<Vertex>>();
        //初始化
        int[] dist = new int[alGraph.getVertexNum()];
        Arrays.fill(dist, Integer.MAX_VALUE / 2);//直接初始化为MAX 后面存在一些问题
        int[] path = new int[alGraph.getVertexNum()];
        Arrays.fill(path, -1);
        boolean[] collected = new boolean[alGraph.getVertexNum()];
        //初始化source 及其邻接点
        dist[sourceIndex] = 0;
        collected[sourceIndex] = true;
        //初始化源点邻接点
        ALEdgeNode firstEdgeNode = alGraph.getVertexList().get(sourceIndex).getEdgeNode();
        if (firstEdgeNode == null) return null;//源点必定有邻接点
        for (ALEdgeNode p = firstEdgeNode; p != null; p = p.getNext()) {
            int pIndex = p.getIndex();
            //dist[pIndex]=Math.min(dist[pIndex],)
            int weight = p.getEdge().getWeightByDistance();
            dist[pIndex] = weight;
            path[pIndex] = sourceIndex;
        }
        dijkstra(alGraph, dist, path, collected);
        //用linkedList模拟栈   将所有路径返回
        for (int targetIndex = 0; targetIndex < alGraph.getVertexNum(); targetIndex++) {
            ArrayList<Vertex> vertexList = new ArrayList<Vertex>();
            LinkedList<Vertex> stack = new LinkedList<Vertex>();
            stack.push(alGraph.getVertexList().get(targetIndex).getVertex());
            for (int x = path[targetIndex]; x != -1; x = path[x]) {
                stack.push(alGraph.getVertexList().get(x).getVertex());
            }
            while (!stack.isEmpty()) {
                Vertex vertex = stack.pop();
                vertexList.add(vertex);
            }
            vertexListAll.add(vertexList);
        }
        return vertexListAll;
    }


    /*
    path[i][j]表示从i到j的最短距离的j的前一个顶点在matrix中的编号
    D[i][j]表示i到j 只经过k的最短距离
     */
    @Override//数组是引用传值！！！
    public void mutipleShortestPath(MGraph mGraph, int[][] path, int[][] D) {

        //1.首先根据邻接矩阵初始化D
        List<ArrayList<Integer>> matrix = mGraph.getVertexMatrix();
        int vertexNum = mGraph.getVertexNum();
        for (int i = 0; i < vertexNum; i++) {
            for (int j = 0; j < vertexNum; j++) {
                D[i][j] = matrix.get(i).get(j);
                if(D[i][j]>0&&D[i][j]<Integer.MAX_VALUE/2){
                    path[i][j]=i;
                }else {
                    path[i][j] = -1;
                }

            }
        }
        //动态规划逐渐加入k
        for (int k = 0; k < vertexNum; k++) {
            for (int i = 0; i < vertexNum; i++) {
                for (int j = 0; j < vertexNum; j++) {
                    if (D[i][j] > D[i][k] + D[k][j]) {
                        D[i][j] = D[i][k] + D[k][j];
                        path[i][j] = path[k][j];
                    }
                }
            }
        }
    }
//
//    public static void main(String[] args) {
//        GraphServiceImpl graphService = new GraphServiceImpl();
//        ALGraph alGraph = graphService.selectALGraph();
//        MGraph graph = graphService.selectMGraph(alGraph);
//        int vertexNum=graph.getVertexNum();
//        int[][]D=new int[vertexNum][vertexNum];
//        int[][]path=new int[vertexNum][vertexNum];
//        graphService.mutipleShortestPath(graph,path,D);
////        for(int i=0;i<vertexNum;i++){
////            for(int j=0;j<vertexNum;j++){
////                System.out.print(i+" "+j+" "+path[i][j]+"\t");
////            }
////            System.out.println();
////        }
////        System.out.println("-------------------");
////        for(int i=0;i<vertexNum;i++){
////            for(int j=0;j<vertexNum;j++){
////                System.out.print(i+" "+j+" "+D[i][j]+"\t");
////            }
////            System.out.println();
////        }
//        List<Vertex> vertexList = graphService.printFloyd(graph, 0, 8, path);
//        System.out.println(vertexList);
//    }

    @Override
    public List<Vertex> printFloyd(MGraph mGraph, int i, int j, int[][] path) {
        List<Vertex> res = new ArrayList<>();

        LinkedList<Integer> stack = new LinkedList<>();
        stack.push(j);
        for (int p = path[i][j]; p != -1; ) {
            stack.push(p);
            if (path[i][p] == -1) {
                break;
            } else {
                p = path[i][p];
            }
        }
    //    stack.push(i);
        while (!stack.isEmpty()) {
            int index = stack.pop();
            res.add(mGraph.getVertexByIndex(index));
        }
        return res;
    }

    @Override
    public MGraph selectMGraph(ALGraph alGraph) {
        return MGraph.ToMGraph(alGraph);
    }

    /*
    MGraph返回的话 边信息忘存了。。。
    所以返回Graph最好
     */
    public DrawGraph selectDrawGraph() {
        EdgeService edgeService = new EdgeService();
        List<Edge> edges = edgeService.selectAll();
        VertexService vertexService = new VertexService();
        List<Vertex> vertices = vertexService.selectAll();
        ArrayList<ArrayList<Boolean>> paths = new ArrayList<ArrayList<Boolean>>();
        //初始化矩阵
        for (int i = 0; i < vertices.size(); i++) {
            ArrayList<Boolean> booleans = new ArrayList<Boolean>();
            for (int j = 0; j < vertices.size(); j++) {
                booleans.add(false);
            }
            paths.add(booleans);
        }
        for (int i = 0; i < edges.size(); i++) {
            Edge edge = edges.get(i);
            int mark1 = edge.getMark1();
            int mark2 = edge.getMark2();

            //无向图
            paths.get(mark1).set(mark2, true);
            paths.get(mark2).set(mark1, true);
        }
        DrawGraph graph = new DrawGraph(edges, vertices, paths);
        return graph;
    }
}

//数组传参传的其实是引用