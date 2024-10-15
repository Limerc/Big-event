package com.itheima;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.concurrent.TimeUnit;

@SpringBootTest //单元方法执行前会初始化Spring容器
public class RedisTest {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Test
    public void testSet(){
        //往Redis中存储一个键值对
        ValueOperations<String, String> Operations = stringRedisTemplate.opsForValue();
        Operations.set("username","zhangsan");
        Operations.set("id","1",15, TimeUnit.SECONDS);
    }

    @Test
    public void testGet(){
        //从Redis中获取一个键值对
        ValueOperations<String, String> Operations = stringRedisTemplate.opsForValue();
        System.out.println(Operations.get("username"));
    }
}
