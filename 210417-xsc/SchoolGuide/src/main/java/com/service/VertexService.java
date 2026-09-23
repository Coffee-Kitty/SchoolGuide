package com.service;


import com.mapper.VertexMapper;
import com.pojo.PageBean;
import com.pojo.Vertex;
import com.utils.SqlSessionFactoryUtil;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.ArrayList;
import java.util.List;

public class VertexService {
    private SqlSessionFactory sqlSessionFactory = SqlSessionFactoryUtil.getSqlSessionFactory();

    /*
    查询所有
     */
    public List<Vertex> selectAll() {

        SqlSession sqlSession = sqlSessionFactory.openSession();
        VertexMapper mapper = sqlSession.getMapper(VertexMapper.class);
        List<Vertex> vertices = mapper.selectAll();

        sqlSession.close();
        return vertices;
    }

    /*
    根据名称查询
     */
    public Vertex selectByName(String name) {
        if(name==null||name.equals(""))return  null;
        SqlSession sqlSession = sqlSessionFactory.openSession();
        VertexMapper mapper = sqlSession.getMapper(VertexMapper.class);
        Vertex vertex = mapper.selectByName(name);

        sqlSession.close();
        return vertex;
    }

    /*
    根据mark查询
     */
    public Vertex selectByMark(int mark) {

        SqlSession sqlSession = sqlSessionFactory.openSession();
        VertexMapper mapper = sqlSession.getMapper(VertexMapper.class);
        Vertex vertex = mapper.selectByMark(mark);
        sqlSession.close();
        return vertex;
    }

    /**
     * 分页查询
     *
     * @param currentPage 当前页码
     * @param pageSize    每页展示条数
     * @return
     */
    public PageBean<Vertex> selectByPage(int currentPage, int pageSize) {
        //2. 获取SqlSession对象
        SqlSession sqlSession = sqlSessionFactory.openSession();
        //3. 获取BrandMapper
        VertexMapper mapper = sqlSession.getMapper(VertexMapper.class);
        //4. 计算开始索引   limit 0,5
        int begin = (currentPage - 1) * pageSize;
        // 计算查询条目数
        int size = pageSize;
        //5. 查询当前页数据  //limit (currentPage - 1) * pageSize,pageSize
        List<Vertex> rows = mapper.selectByPage(begin, size);
        //6. 查询总记录数
        int totalCount = mapper.selectTotalCount();
        //7. 封装PageBean对象
        PageBean<Vertex> pageBean = new PageBean<Vertex>();
        pageBean.setRows(rows);
        pageBean.setTotalCount(totalCount);

        //8. 释放资源
        sqlSession.close();
        return pageBean;
    }

    /*
    增加顶点
     */
    public boolean add(Vertex vertex) {
        boolean flag=false;
        //首先查询是否已存在
        if(vertex==null||selectByName(vertex.getName())!=null){
            return flag;
        }
        SqlSession sqlSession = sqlSessionFactory.openSession();
        VertexMapper mapper = sqlSession.getMapper(VertexMapper.class);

        mapper.solveAutoIncre();//解决自增不连续问题
        mapper.add(0, vertex.getName(), vertex.getMark(), vertex.getData(),vertex.getLeft(),vertex.getTop());//据说  可以把该列（id）的值设为null或者0，这样MySQL会自己做处理

        //记得提交事务
        sqlSession.commit();
        sqlSession.close();
        return true;
    }

    public boolean add(ArrayList<Vertex> vertexArrayList) {
        boolean flag=false;
        //首先查询是否已存在
        if(vertexArrayList==null||vertexArrayList.size()==0){
            return flag;
        }
        SqlSession sqlSession = sqlSessionFactory.openSession();
        VertexMapper mapper = sqlSession.getMapper(VertexMapper.class);
        int i = mapper.selectTotalCount();
        System.out.println(i);

        mapper.solveDeafultId();

       for(Vertex vertex:vertexArrayList){
           mapper.add(vertex.getId(),vertex.getName(),vertex.getMark(),vertex.getData(),vertex.getLeft(),vertex.getTop());
       }

        //记得提交事务
        sqlSession.commit();
        sqlSession.close();
        return true;
    }

    /*
    删除顶点  同时删除响应的边
     */
    public boolean delete(Vertex vertex) {

        SqlSession sqlSession = sqlSessionFactory.openSession();
        VertexMapper mapper = sqlSession.getMapper(VertexMapper.class);
        boolean flag = mapper.delete(vertex.getId());
        //记得提交事务
        sqlSession.commit();
        sqlSession.close();
        return true;
    }

    /**
     * 批量删除
     *
     * @param ids
     */
    public void deleteByIds(int[] ids) {

        SqlSession sqlSession = sqlSessionFactory.openSession();
        VertexMapper mapper = sqlSession.getMapper(VertexMapper.class);
        mapper.deleteByIds(ids);
        sqlSession.commit();//提交事务
        //5. 释放资源
        sqlSession.close();
    }

    /*
    改
     */
    public boolean update(Vertex vertex) {

        SqlSession sqlSession = sqlSessionFactory.openSession();
        VertexMapper mapper = sqlSession.getMapper(VertexMapper.class);
        boolean flag = mapper.updateById(vertex);
        //记得提交事务
        sqlSession.commit();
        sqlSession.close();
        return true;
    }

}
