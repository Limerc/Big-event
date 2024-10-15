package com.itheima.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.mapper.ArticleMapper;
import com.itheima.pojo.Article;
import com.itheima.pojo.PageBean;
import com.itheima.service.ArticleService;
import com.itheima.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;
    @Override
    public void add(Article article) {
        //补充属性值
        article.setCreateTime(LocalDateTime.now());
        article.setUpdateTime(LocalDateTime.now());
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        article.setCreateUser(id);
        articleMapper.add(article);
    }

    @Override
    public PageBean<Article> list(Integer pageNum, Integer pageSize, Integer categoryId, String state) {
        // 1.创建PageBean对象
        PageBean<Article> pb = new PageBean<>();

        // 2.开启分页查询：运用mybatis的分页插件pageHelper
        PageHelper.startPage(pageNum, pageSize);
        // pageHelper会将PageNum和PageSize拼接到SQL之后

        // 3.调用Mapper查询数据：只能查询登录用户自己创建的文章
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userid = (Integer) map.get("id");
        List<Article> as = articleMapper.list(userid, categoryId, state);
        // 因为使用了PageHelper，故会返回一个List接口的实现类对象
        Page<Article> p = (Page<Article>) as;
        // Page中提供了方法，可以获取PageHelper分页查询后，得到的总记录条数和当前页数据

        // 将数据填充到PageBean对象中
        pb.setTotal(p.getTotal());   // 总记录条数
        pb.setItems(p.getResult());  // 当前页数据集

        return pb;
    }

    @Override
    public Article detail(Integer id) {
        // 根据主键ID查询文章
        return articleMapper.detail(id);
    }

    @Override
    public void renew(Article article) {
        //补充属性值
        article.setUpdateTime(LocalDateTime.now());
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        article.setCreateUser(id);
        articleMapper.renew(article);
    }

    @Override
    public void delete(Integer id) {
        articleMapper.delete(id);
    }
}
