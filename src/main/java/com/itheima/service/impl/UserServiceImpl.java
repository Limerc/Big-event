package com.itheima.service.impl;

import com.itheima.mapper.UserMapper;
import com.itheima.pojo.User;
import com.itheima.service.UserService;
import com.itheima.utils.Md5Util;
import com.itheima.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    //Alt+Enter 快速生成继承方法
    @Override
    public User findByUsername(String username) {
        // 调用Mapper层的方法进行SQL查询
        User u = userMapper.findByUsername(username);
        return u;
    }

    @Override
    public void register(String username, String password) {
        // 加密：利用MD5加密算法，利用MD5工具类
        String md5Password = Md5Util.getMD5String(password);
        // 添加
        userMapper.add(username, md5Password);
    }

    @Override
    public void update(User user) {
        user.setUpdateTime(LocalDateTime.now()); //更新时间
        userMapper.update(user);
    }

    @Override
    public void updateAvatar(String avatarUrl){
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        userMapper.updateAvatar(avatarUrl,id);
    }

    @Override
    public void updatePwd(String newPwd){
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        userMapper.updatePwd(Md5Util.getMD5String(newPwd),id);
    }
}

/*
 * 由于 UserServiceImpl 实现了 UserService 接口，任何 UserService 的实例实际上都是
 * UserServiceImpl 的实例。因此，当通过 UserService 接口调用方法时，
 * 实际上是在调用 UserServiceImpl 中覆盖的方法。
 */