package com.mapper;

import com.pojo.Edge;
import com.service.EdgeService;
import org.junit.Test;

import java.util.List;
/*
请注意 edgeService 提供功能不涉及逻辑判断
 */

public class EdgeTest {

    /*
    查询所有
     */
    @Test
    public void testSelectAll(){
        EdgeService edgeService = new EdgeService();
        List<Edge> edges = edgeService.selectAll();
        System.out.println(edges);
    }

    /*
    依据mark1  mark2 查询
     */
    @Test
    public void testSelectByMarks(){
        EdgeService edgeService = new EdgeService();
        Edge edge = edgeService.selectByMarks(0, 1);
        Edge edge1 = edgeService.selectByMarks(0, 1);
        //有问题这里!!!   明明只根据mark来查
        System.out.println(edge1);
    }

    /*
    简单的添加  当id值非法时 数据库自行向后自增了
    解决了删除id导致之后插入  自增id不连续问题
     */
    @Test
    public  void testAdd()
    {
        EdgeService edgeService = new EdgeService();
        boolean flag = edgeService.add(new Edge(32,0,1,"200m","10min","5min"));
        System.out.println(flag);
    }

    /*
    依据id删除
     */
    @Test
    public void testDelete(){
        EdgeService edgeService = new EdgeService();
        boolean flag = edgeService.delete(new Edge(32,0,1,"200m","10min","5min"));
        System.out.println(flag);
    }

}
