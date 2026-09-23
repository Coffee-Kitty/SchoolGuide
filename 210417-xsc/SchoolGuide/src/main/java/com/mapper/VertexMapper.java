package com.mapper;

import com.pojo.Vertex;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface VertexMapper {
    //解决自增不连续问题  及清空后id不从1增长问题
    @Update("ALTER TABLE vertex AUTO_INCREMENT = 1;")
    void solveAutoIncre();

    @Update("ALTER TABLE vertex ALTER COLUMN id SET DEFAULT 0")
    void solveDeafultId();
    /*
    根据名称查询
     */

    Vertex selectByName(String name);

    /*
    根据mark查询
     */
    @Select("select * from vertex where mark=#{mark}")
    Vertex selectByMark(int mark);

    List<Vertex> selectAll();

    /**
     * 分页查询
     *
     * @param begin
     * @param size
     * @return
     */
    @Select("select * from vertex limit #{begin} , #{size}")
    @ResultMap("VertexResult")
    List<Vertex> selectByPage(@Param("begin") int begin, @Param("size") int size);

    /**
     * 查询总记录数
     *
     * @return
     */
    int selectTotalCount();

    /*
    增
     */
    boolean add(@Param("id") int vid, @Param("name") String name, @Param("mark") int mark, @Param("data") String data,@Param("left")int left,@Param("top")int top);

    /*
    删
     */
    @Delete("delete from vertex where id=#{id}")
    @ResultMap("VertexResult")
    boolean delete(int id);

    /**
     * 批量删除
     *
     * @param ids
     */
    void deleteByIds(@Param("ids") int[] ids);

    /*
    改
     */
    @Update("update vertex set `id`=#{id},`name`=#{name},`mark`=#{mark},`data`=#{data},`left`=#{left},`top`=#{top} where id=#{id}")
    @ResultMap("VertexResult")
    //boolean update(@Param("id") int id, @Param("name") String name, @Param("mark") int mark, @Param("data") String data);
    boolean updateById(Vertex vertex);


}
