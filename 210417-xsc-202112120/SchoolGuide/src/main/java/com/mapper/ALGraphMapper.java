package com.mapper;

import com.pojo.Edge;
import com.pojo.Vertex;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/*
查找视图
 */
public interface ALGraphMapper {

    //1.查询顶点总数
    int selectVertexNum();

    //2.查询边总数
    int selectEdgeNum();

    //3.根据顶点的mark查询到  其邻接的边
    List<Edge> selectAdjEdgeByMark(int mark);

    //4.根据顶点的id查询顶点
    @Select("select * from vertex where id=#{vid}")
    Vertex selectVertexById(int vid);
}
