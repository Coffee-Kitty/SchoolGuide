package com.mapper;

import com.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;


public interface UserMapper {

    /**
     * 1.
     * 根据用户名和密码查询用户对象
     *
     * @param username
     * @param passwd
     * @return
     */
    @Select("select * from user where username = #{username} and passwd =#{passwd}")
    User selectByNameAndPasswd(@Param("username") String username, @Param("passwd") String passwd);

    /**
     * 2.
     * 根据用户名查询用户对象
     *
     * @param username
     * @return
     */
    User selectByUserName(String username);

    //3.查询所有
    List<User> selectAll();

    //解决自增不连续问题  及清空后id不从1增长问题
    @Update("ALTER TABLE user AUTO_INCREMENT = 1;")
    void solveAutoIncre();

    @Update("ALTER TABLE user ALTER COLUMN id SET DEFAULT 0")
    void solveDeafultId();


    /**
     * 5.
     * 添加用户
     *
     * @param user
     */
    @Insert("insert into user values(null,#{username},#{passwd},#{phonenumber})")
    void add(User user); //#{username} 表示将 User 对象的 username 属性值插入到 SQL 语句中。
}
