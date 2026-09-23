//package 弃用;
//
//import com.pojo.Edge;
//import com.pojo.Graph;
//import com.pojo.Vertex;
//import com.service.EdgeService;
//import com.service.VertexService;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class GraphAllService {
//
//    //地图
//    private  Graph graph;
//
//    //供调用的service
//    private EdgeService edgeService;
//    private VertexService vertexService;
//    //便于修改的增广矩阵
//    ArrayList<ArrayList<Boolean>> paths;
//
//    //1.构造
//    public GraphAllService(){
//        init();
//    }
//
//
//    //2.初始化
//    public void init(){
//        edgeService = new EdgeService();
//        List<Edge> edges = edgeService.selectAll();
//        vertexService = new VertexService();
//        List<Vertex> vertices = vertexService.selectAll();
//         paths = new ArrayList<ArrayList<Boolean>>();
//        //初始化增广矩阵
//        for(int i=0;i<vertices.size();i++)
//        {
//            ArrayList<Boolean> booleans = new ArrayList<Boolean>();
//            for(int j=0;j<vertices.size();j++)
//            {
//                booleans.add(false);
//            }
//            paths.add(booleans);
//        }
//        for(int i=0;i<edges.size();i++)
//        {
//            Edge edge = edges.get(i);
//            int mark1 = edge.getMark1();
//            int mark2 = edge.getMark2();
//
//            //无向图
//            paths.get(mark1).set(mark2,true);
//            paths.get(mark2).set(mark1,true);
//        }
//        graph=new Graph(edges,vertices,paths);
//    }
//
//    //通过景点名称去查询  查不到返回null
//    public Vertex selectByName(String name){
//        if(name==null)return null;
//
//        Vertex vertex = vertexService.selectByName(name);
//        return vertex;
//    }
//
//
//    //增
//    //景点为null或 景点名称或id已存在返回false
//    public boolean addVertex(Vertex vertex) {
//        boolean flag=false;
//        //首先查询是否已存在
//        if(vertex==null||vertexService.selectByName(vertex.getName())!=null){
//            return flag;
//        }
//        //然后调用方法增加到数据库
//        flag= vertexService.add(vertex);
//        if(!flag)return flag;
//        //然后修改graph
//        flag = graph.getVertexList().add(vertex);
//        return flag;
//    }
//    //增加边 核心是两顶点间有边后 就进行比较   如果除了id外其他都相同  则认为相同
//    public boolean addEdge(Edge edge){
//        boolean flag=false;
//        //首先查询是否已存在
//        if(edge==null){
//            return flag;
//        }
//        Edge edge1 = edgeService.selectByMarks(edge.getMark1(),edge.getMark2());
//        if(edge.equals(edge1)){
//            return flag;
//        }
//        //然后修改graph
//        flag = graph.getEdgeList().add(edge);
//        paths.get(edge.getMark1()).set(edge.getMark2(),true);
//        paths.get(edge.getMark2()).set(edge.getMark1(),true);
//        graph.setPath(paths);
//        //再然后调用方法增加到数据库
//        flag= edgeService.add(edge);
//        return flag;
//    }
//
//
//    //删
//    public boolean deleteEdge(Edge edge){
//        boolean flag=false;
//        //首先查询是否已存在
//        if(edge==null){
//            return flag;
//        }
//        Edge edge1 = edgeService.selectByMarks(edge.getMark1(),edge.getMark2());
//        if(!edge.equals(edge1)){
//            return flag;
//        }
//        //然后修改graph
//        flag = graph.getEdgeList().remove(edge);
//        paths.get(edge.getMark1()).set(edge.getMark2(),false);
//        paths.get(edge.getMark2()).set(edge.getMark1(),false);
//        graph.setPath(paths);
//        graph.setEdgeNum(graph.getEdgeNum()-1);
//        //再然后调用方法删除
//        flag= edgeService.delete(edge);
//        return flag;
//    }
//
//    public boolean deleteVertex(Vertex vertex){
//        boolean flag=false;
//        //首先查询是否已存在
//        if(vertex==null||vertexService.selectByName(vertex.getName())==null){
//            return flag;
//        }
//        //然后修改graph
//        flag = graph.getVertexList().remove(vertex);
//        //再然后调用方法删除顶点
//        flag= vertexService.delete(vertex);
//        graph.setVertexNum(graph.getVertexNum()-1);
//        //删除相应的边
//        for (int i = 0; i < graph.getEdgeList().size(); i++) {
//            Edge edge = graph.getEdgeList().get(i);
//            if(edge.isConnect(vertex)){
//                deleteEdge(edge);//调用删边方法
//            }
//        }
//        return flag;
//
//    }
//
//
//    //改
//    public boolean updateVertex(Vertex vertex) {
//        boolean flag=false;
//        //首先查询是否已存在
//        if(vertex==null||vertexService.selectByName(vertex.getName())==null){
//            return flag;
//        }
//        //然后修改graph
//        for (Vertex vertex1 : graph.getVertexList()) {
//            if(vertex1.getName().equals(vertex.getName())){
//                vertex1.setName(vertex.getName());
//                flag=true;
//            }
//        }
//        if(!flag)return flag;
//        //再然后调用方法增加到数据库
//        flag= vertexService.update(vertex);
//        return flag;
//    }
//    //根据边的id修改
//    public boolean updateEdge(Edge edge){
//        boolean flag=false;
//        //首先查询是否已存在
//        if(edge==null){
//            return flag;
//        }
//        Edge edge1 = edgeService.selectByMarks(edge.getMark1(),edge.getMark2());
//        if(edge.equals(edge1)){
//            return flag;
//        }
//
//        //把edge1删了  再把edge添加进去
//        flag=deleteEdge(edge1);
//        flag=addEdge(edge);
//        return flag;
//    }
//
//
//
//
//    //get方法  直接将整个图返回
//    public  Graph getGraph() {
//        return graph;
//    }
//
//    public  void setGraph(Graph graph) {
//        this.graph = graph;
//    }
//}
