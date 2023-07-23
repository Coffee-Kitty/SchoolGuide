package com.mapper;

/*
同样此处逻辑操作也不涉及
 */

import com.pojo.Vertex;
import com.service.VertexService;
import org.junit.Test;

import java.util.List;

public class VertexTest {

    @Test
    public void test_selectByName(){
        VertexService vertexService = new VertexService();
        Vertex v = vertexService.selectByName("励学楼");
        System.out.println(v);
    }


    @Test
    public void test_selectAll(){
        VertexService vertexService = new VertexService();
        List<Vertex> v = vertexService.selectAll();
        System.out.println(v);
    }
    /*
    增加一个顶点   已经默认id为0  其余景点信息必须包含
     */
    @Test
    public void test_add(){
        VertexService vertexService = new VertexService();
        boolean flag = vertexService.add(new Vertex("中心花园",14,"中心花园"));
        System.out.println(flag);
    }
    /*
    本质上是通过id来删除
     */
    @Test
    public void test_delete(){
        VertexService vertexService = new VertexService();
        boolean flag = vertexService.delete(new Vertex(16,"中心花园",14,"中心花园"));
        System.out.println(flag);
    }
    /*
    本质通过id来修改对应的景点
     */
  @Test
    public void test_update(){
      VertexService vertexService = new VertexService();
      boolean flag = vertexService.update(new Vertex(14,"中心花园123",14,"中心花园"));
      System.out.println(flag);
    }

}
