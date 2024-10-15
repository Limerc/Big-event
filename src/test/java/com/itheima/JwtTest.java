package com.itheima;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Date;

public class JwtTest {

    @Test
    public void testGen(){
        Map<String, Object> claims = new HashMap<>();
        claims.put("id",1);
        claims.put("username","张三");
        //生产jwt
        String token = JWT.create()
                .withClaim("user",claims)//添加以"user"为键的载荷
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 12))//添加过期时间12小时
                .sign(Algorithm.HMAC256("itheima"));//指定算法、配置秘钥
        //输出token
        System.out.println(token);
    }

    @Test
    public void testParse(){
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9." +
                "eyJ1c2VyIjp7ImlkIjoxLCJ1c2VybmFtZSI6IuW8oOS4iSJ9LCJleHAiOjE3MjIxOTcxNjJ9." +
                "12y42aTg-l82qekZAeqU2R2m_5HXS_IxY2hSI-T2UqY";
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256("itheima")).build();   //验证算法和秘钥，生成验证器
        DecodedJWT decodedJWTjwt = verifier.verify(token);// 验证token生成解析后的JWT对象
        Map<String, Claim> claims = decodedJWTjwt.getClaims(); //获取载荷
        //这里注意要接受Claim类型，否则会报错
        System.out.println(claims.get("user"));  //获取以"user"为键的载荷

        /*
        篡改头部和尾部的数据——验证失败
        篡改秘钥——验证失败
        token过期——验证失败
         */
    }
}
