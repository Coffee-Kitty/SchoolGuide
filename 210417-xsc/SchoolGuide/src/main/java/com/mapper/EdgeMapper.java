package com.mapper;

import com.pojo.Edge;
import org.apache.ibatis.annotations.*;

import java.util.List;


public interface EdgeMapper {

    /*
   根据两临界点查询
    */
    @Select("select * from edge where mark1=#{mark1} and mark2=#{mark2}")
    @ResultMap("EdgeResult")
    //这里还需单独注释返回集合 否则walk_time无法映射为walkTime
    Edge selectByMarks(@Param("mark1") int mark1, @Param("mark2") int mark2);//@Param指明数据库列名

    //查询所有
    List<Edge> selectAll();


    //增
    boolean add(@Param("mark1") int mark1, @Param("mark2") int mark2, @Param("distance") String distance, @Param("walkTime") String walkTime, @Param("rideTime") String rideTime);

    //根据id删边
    @Delete("delete from edge where id=#{id}")
    @ResultMap("EdgeResult")
    boolean delete(int id);

    //解决自增不连续问题  及清空后id不从1增长问题
    @Update("ALTER TABLE edge AUTO_INCREMENT = 1;")
    void solveAutoIncre();

    @Update("ALTER TABLE edge ALTER COLUMN id SET DEFAULT 0")
    void solveDeafultId();

    /**
     * 批量删除根据id
     *
     * @param ids
     */
    void deleteByIds(@Param("ids") int[] ids);

    /*
        改
    */
    @Update("update edge " +
            "set `id`=#{id},`mark1`=#{mark1},`mark2`=#{mark2},`distance`=#{distance},`walk_time`=#{walkTime},`ride_time`=#{rideTime}" +
            " where `id`=#{id}")
    @ResultMap("EdgeResult")
    boolean updateById(Edge edge);


    /**
     * 分页查询
     *
     * @param begin
     * @param size
     * @return
     */

    List<Edge> selectByPage(@Param("begin") int begin, @Param("size") int size);

    /**
     * 查询总记录数
     *
     * @return
     */
    int selectTotalCount();

}

//注：方法不可有重名
//在使用@Select等注解的情况下，方法名即为mapper的id，id相同。重载的方法会报这个错