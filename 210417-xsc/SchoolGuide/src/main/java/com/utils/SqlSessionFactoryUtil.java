package com.utils;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

/**
 * SqlSession工具类
 */
public class SqlSessionFactoryUtil {

    private static SqlSessionFactory sqlSessionFactory = null;

    static {
        String resource = "mybatis-config.xml";
        InputStream inputStream = null;
        //Reader reader=null;
        try {
            inputStream = Resources.getResourceAsStream(resource);
            // reader=Resources.getResourceAsReader(resource);

            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            // sqlSessionFactory =  new SqlSessionFactoryBuilder().build(reader);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static SqlSessionFactory getSqlSessionFactory() {
        return sqlSessionFactory;
    }
}
