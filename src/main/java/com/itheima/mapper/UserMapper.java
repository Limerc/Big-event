package com.itheima.mapper;

import com.itheima.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/*
 * Mapper接口：用于编写与数据库的交互SQL语句
 */
@Mapper
public interface UserMapper {

    //根据用户名查询用户
    @Select("SELECT * FROM user WHERE username = #{username}")
    User findByUsername(String username);

    //添加用户
    @Insert("INSERT INTO user(username, password, create_time, update_time)" +
            " VALUES (#{username}, #{password}, NOW(), NOW())")
    //利用数据库的Now函数来获取当前时间
    void add(String username, String password);

    //更新用户信息
    @Update("UPDATE user SET nickname = #{nickname},email = #{email}," +
            "update_time = #{updateTime} where id = #{id}")
    void update(User user);

    @Update("UPDATE user set user_pic = #{avatarUrl}, update_time = NOW() where id = #{id}")
    void updateAvatar(String avatarUrl,Integer id);

    @Update("UPDATE user SET password = #{md5String},update_time = NOW() WHERE id = #{id}")
    void updatePwd(String md5String, Integer id);
}
