package com.itheima.service;

import com.itheima.pojo.Article;
import com.itheima.pojo.PageBean;

public interface ArticleService {
    // 添加文章
    void add(Article article);

    // 条件分页列表查询
    PageBean<Article> list(Integer pageNum, Integer pageSize, Integer categoryId, String state);

    // 查询文章详情
    Article detail(Integer id);

    // 更新文章内容
    void renew(Article article);

    // 删除文章
    void delete(Integer id);
}
