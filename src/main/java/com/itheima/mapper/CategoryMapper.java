package com.itheima.mapper;

import com.itheima.pojo.Category;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CategoryMapper {
    // 添加分类
    @Insert("INSERT INTO category(category_name,category_alias,create_user," +
            "create_time,update_time) VALUES (#{categoryName},#{categoryAlias}" +
            ",#{createUser},#{createTime},#{updateTime})")
    void add(Category category);

    // 查询所有分类
    @Select("SELECT * FROM category WHERE create_user = #{id}")
    List<Category> list(Integer id);

    // 根据id查询分类
    @Select("SELECT * FROM category WHERE id = #{id}")
    Category findById(Integer id);

    // 更新文章信息
    @Update("UPDATE category SET category_name = #{categoryName},category_alias = " +
            "#{categoryAlias},update_time = #{updateTime} WHERE id = #{id}")
    void update(Category category);

    // 删除分类
    @Delete("DELETE FROM category WHERE id = #{id}")
    void delete(Integer id);
}
