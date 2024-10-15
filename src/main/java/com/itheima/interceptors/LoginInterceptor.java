package com.itheima.interceptors;

import com.itheima.utils.JwtUtil;
import com.itheima.utils.ThreadLocalUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;
@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 令牌验证
        String token = request.getHeader("Authorization");
        // 验证token
        try{
            // 从redis中获取token并核对
            ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
            String RedisToken = operations.get(token);
            if(RedisToken == null){
                throw new RuntimeException();
            }
            Map<String, Object> claims = JwtUtil.parseToken(token);
            // 把业务数据存储到ThreadLocal中
            ThreadLocalUtil.set(claims);
            return true; // 验证通过
        }catch (Exception e){
            //http状态码401，未登录
            response.setStatus(401);
            return false; // 验证失败
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 业务处理完成后，清除ThreadLocal中的数据
        ThreadLocalUtil.remove();
    }
}
