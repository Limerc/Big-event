package com.itheima.mapper;

import com.github.pagehelper.Page;
import com.itheima.pojo.Article;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ArticleMapper {
    // 添加文章
    @Insert("INSERT INTO article(title, content, cover_img, state, category_id, " +
            "create_user, create_time, update_time) VALUES(#{title}, #{content}, #{coverImg}," +
            " #{state}, #{categoryId}, #{createUser}, #{createTime}, #{updateTime}) ")
    void add(Article article);

    // 利用映射配置文件来写动态SQL
    List<Article> list(Integer userid, Integer categoryId, String state);

    // 根据ID查询文章详情
    @Select("SELECT * FROM article WHERE id = #{id}")
    Article detail(Integer id);

    // 根据ID更新文章
    @Update("UPDATE article SET title = #{title}, content = #{content}, cover_img = " +
            "#{coverImg}, state = #{state}, category_id = #{categoryId}, update_time = " +
            "#{updateTime} WHERE id = #{id}")
    void renew(Article article);

    // 根据ID删除文章
    @Delete("DELETE FROM article WHERE id = #{id}")
    void delete(Integer id);
}
