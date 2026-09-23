package com.service;

import com.mapper.EdgeMapper;
import com.pojo.Edge;
import com.pojo.PageBean;
import com.utils.SqlSessionFactoryUtil;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;

/*
边的服务   只提供基础服务  所有的逻辑判断都由上层自行进行处理
 */
public class EdgeService {
    private SqlSessionFactory sqlSessionFactory = SqlSessionFactoryUtil.getSqlSessionFactory();

    /*
    查询所有
     */
    public List<Edge> selectAll() {

        SqlSession sqlSession = sqlSessionFactory.openSession();
        EdgeMapper mapper = sqlSession.getMapper(EdgeMapper.class);
        List<Edge> edges = mapper.selectAll();
        sqlSession.close();
        return edges;
    }

    /*
    根据两邻接点查询是否已经存在
     */
    public Edge selectByMarks(int mark1, int mark2) {

        SqlSession sqlSession = sqlSessionFactory.openSession();
        EdgeMapper mapper = sqlSession.getMapper(EdgeMapper.class);
        Edge edgeRes = mapper.selectByMarks(mark1, mark2);
        sqlSession.close();
        return edgeRes;
    }

    //增
    public boolean add(Edge edge) {

        SqlSession sqlSession = sqlSessionFactory.openSession();
        EdgeMapper mapper = sqlSession.getMapper(EdgeMapper.class);
        int i = mapper.selectTotalCount();
        if(i!=0){
            mapper.solveAutoIncre();//解决删除后 自增id不连续问题 !!!!!!一定要注意
        }
        boolean flag = mapper.add(edge.getMark1(), edge.getMark2(), edge.getDistance(), edge.getWalkTime(), edge.getRideTime());
        //记得提交事务
        sqlSession.commit();
        sqlSession.close();
        return flag;
    }
    //尽可能的添加数据  对于不符合格式的数据  失败了也不要紧
    public boolean add(List<Edge> edgeList) {

        if(edgeList==null||edgeList.size()==0){
            return false;
        }
        SqlSession sqlSession = sqlSessionFactory.openSession();
        EdgeMapper mapper = sqlSession.getMapper(EdgeMapper.class);
        int i = mapper.selectTotalCount();
        mapper.solveDeafultId();
        boolean flag=false;
        for(Edge edge:edgeList){
           flag = mapper.add(edge.getMark1(), edge.getMark2(), edge.getDistance(), edge.getWalkTime(), edge.getRideTime());
        }
           //记得提交事务
        sqlSession.commit();
        sqlSession.close();
        return flag;
    }

    /*
    删
     */
    public boolean delete(Edge edge) {

        SqlSession sqlSession = sqlSessionFactory.openSession();
        EdgeMapper mapper = sqlSession.getMapper(EdgeMapper.class);
        boolean flag = mapper.delete(edge.getId());
        //记得提交事务
        sqlSession.commit();
        sqlSession.close();
        return flag;
    }

    /**
     * 批量删除
     *
     * @param ids
     */
    public void deleteByIds(int[] ids) {

        SqlSession sqlSession = sqlSessionFactory.openSession();
        EdgeMapper mapper = sqlSession.getMapper(EdgeMapper.class);
        mapper.deleteByIds(ids);
        sqlSession.commit();//提交事务
        //5. 释放资源
        sqlSession.close();
    }

    /*
        改
    */
    public boolean update(Edge edge) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        EdgeMapper mapper = sqlSession.getMapper(EdgeMapper.class);
        boolean flag = mapper.updateById(edge);
        //记得提交事务
        sqlSession.commit();
        sqlSession.close();
        return true;
    }

    /**
     * 分页查询
     *
     * @param currentPage 当前页码
     * @param pageSize    每页展示条数
     * @return
     */
    public PageBean<Edge> selectByPage(int currentPage, int pageSize) {
        //2. 获取SqlSession对象
        SqlSession sqlSession = sqlSessionFactory.openSession();
        //3. 获取BrandMapper
        EdgeMapper mapper = sqlSession.getMapper(EdgeMapper.class);
        //4. 计算开始索引   limit 0,5
        int begin = (currentPage - 1) * pageSize;
        // 计算查询条目数
        int size = pageSize;
        //5. 查询当前页数据  //limit (currentPage - 1) * pageSize,pageSize
        List<Edge> rows = mapper.selectByPage(begin, size);
        //6. 查询总记录数
        int totalCount = mapper.selectTotalCount();
        //7. 封装PageBean对象
        PageBean<Edge> pageBean = new PageBean<Edge>();
        pageBean.setRows(rows);
        pageBean.setTotalCount(totalCount);

        //8. 释放资源
        sqlSession.close();
        return pageBean;
    }

}
