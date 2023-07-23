package com.service;


import com.mapper.UserMapper;
import com.pojo.User;
import com.utils.SqlSessionFactoryUtil;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;


public class UserService {
    private SqlSessionFactory sqlSessionFactory;

    /**
     * 1.
     * 根据用户名和密码查询用户对象
     *
     * @param username
     * @param passwd
     * @return
     */
    public User selectByUsernameAndPasswd(String username, String passwd) {
        if (username == null || username.equals("") || passwd == null || passwd.equals("")) {
            return null;
        }
        sqlSessionFactory = SqlSessionFactoryUtil.getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        User user = mapper.selectByNameAndPasswd(username, passwd);
        sqlSession.close();
        return user;
    }

    /**
     * 2.
     * 根据用户名查询用户对象
     *
     * @param username
     * @return
     */
    public User selectByUsername(String username) {

        sqlSessionFactory = SqlSessionFactoryUtil.getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        User user = mapper.selectByUserName(username);
        sqlSession.close();

        return user;
    }

    /**
     * 4.
     * 查询所有用户
     *
     * @return
     */
    public List<User> selectAll() {
        sqlSessionFactory = SqlSessionFactoryUtil.getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        List<User> users = mapper.selectAll();
        sqlSession.close();
        return users;
    }

    /**
     * 5.
     * 添加用户
     *
     * @param user
     * @return
     */
    public void addUser(User user) {
        sqlSessionFactory = SqlSessionFactoryUtil.getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        mapper.solveAutoIncre();//!!!
        mapper.add(user);
        sqlSession.commit();
        sqlSession.close();

    }


}
