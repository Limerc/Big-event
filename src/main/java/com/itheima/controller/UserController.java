package com.itheima.controller;
import com.itheima.pojo.Result;  // 引入Result类
import com.itheima.pojo.User;    // 引入User类
import com.itheima.service.UserService;
import com.itheima.utils.JwtUtil;
import com.itheima.utils.Md5Util;
import com.itheima.utils.ThreadLocalUtil;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/*
 * 控制器类：处理用户相关的请求，控制浏览器访问的直接返回结果
 */
@RestController
@RequestMapping("/user")
@Validated
public class UserController {
    @Autowired
    private UserService userService; //创建一个UserService的实例，用于处理业务逻辑
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @PostMapping("/register")
    public Result register(@Pattern(regexp = "^\\S{5,16}$") String username, @Pattern(regexp = "^\\S{5,16}$") String password) {
        // 查询用户：需要用到service方法
        User u = userService.findByUsername(username); // 调用service方法，查询用户是否存在
        System.out.println(u);
        if (u == null){
            // 用户不存在，可以注册
            // 注册用户
            userService.register(username, password);  // Alt+Enter 快速生成方法
            return Result.success();
        }
        else{
            // 用户已存在
            return Result.error("用户名已存在");
        }
    }
    @PostMapping("/login")
    public Result<String> login(@Pattern(regexp = "^\\S{5,16}$") String username, @Pattern(regexp = "^\\S{5,16}$") String password) {
        // 根据用户查询用户名
        System.out.println(username);
        User loginUser = userService.findByUsername(username);

        // 判断用户名是否存在
        if (loginUser == null) {
            return Result.error("用户名不存在");
        }

        // 判断密码是否正确 loginUser中的password是加密后的密码
        // 比较思路：将用户输入的密码加密后与数据库中的密码进行比较
        if(Md5Util.getMD5String(password).equals(loginUser.getPassword())){
            // 登录成功
            Map<String, Object> claims = new HashMap<>();
            claims.put("id",loginUser.getId());
            claims.put("username",loginUser.getUsername());
            String token = JwtUtil.genToken(claims);
            //将token存储到redis中
            ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
            operations.set(token,token,3, TimeUnit.HOURS);
            return Result.success(token);
        }
        return Result.error("密码错误");
    }
    @GetMapping("/userinfo")
    public Result<User> userInfo(/*@RequestHeader(name = "Authorization") String token*/) {
        /*
        解码用户名
        Map<String, Object> map = JwtUtil.parseToken(token);
        String usernaame = (String) map.get("username"); 强制类型转换
         */

        // 从ThreadLocal中获取业务数据，String的键，Object的值
        Map<String, Object> map = ThreadLocalUtil.get();
        String usernaame = (String) map.get("username");

        // 根据用户名查询用户信息
        User user = userService.findByUsername(usernaame);
        return Result.success(user);
    }
    @PutMapping("/update")
    public Result update(@RequestBody @Validated User user){
        // 更新用户信息
        userService.update(user);
        return Result.success();
    }
    @PatchMapping("/updateAvatar")
    public Result updateAvatar(@RequestParam @URL String avatarUrl){
        userService.updateAvatar(avatarUrl);
        return Result.success();
    }
    @PatchMapping("/updatePwd")
    public Result updatePwd(@RequestBody Map<String, String> params,@RequestHeader("Authorization") String token){
        //校验参数
        String old_pwd = params.get("old_pwd");
        String new_pwd = params.get("new_pwd");
        String re_pwd = params.get("re_pwd");
        if(!StringUtils.hasLength(old_pwd) || !StringUtils.hasLength(new_pwd)
                ||!StringUtils.hasLength(re_pwd)){
            return Result.error("参数不能为空");
        }

        // 验证密码
        Map<String, Object> map = ThreadLocalUtil.get();
        String username = (String) map.get("username");
        User loginUser = userService.findByUsername(username);
        if(!loginUser.getPassword().equals(Md5Util.getMD5String(old_pwd))){
            return Result.error("原密码错误");
        }

        // 验证new_pwd和re_pwd是否一致
        if(!new_pwd.equals(re_pwd)){
            return Result.error("两次填写的新密码不一致");
        }

        // 更新密码
        userService.updatePwd(new_pwd);

        // 删除Redis中对应的token
        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
        operations.getOperations().delete(token);

        return Result.success();
    }
}
