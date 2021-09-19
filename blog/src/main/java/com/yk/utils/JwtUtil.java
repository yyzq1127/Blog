package com.yk.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Date;

/**
 * @author yk
 * @version 1.0
 * @date 2021/4/3 8:48
 */
public class JwtUtil {


    private static long expireTime = 259200000;
    private static String secretKey = "qwertyuiopasdfghjklzxcvbnm";

    //error 无法获取yml配置文件中的数据
    //token保存时间
    @Value("${token.expireTime}")
    public static void setExpireTime(long expireTime) {
        JwtUtil.expireTime = expireTime;
    }

    @Value("${token.secretKey}")
    public static void setSecretKey(String secretKey) {
        JwtUtil.secretKey = secretKey;
    }

    /**
     * 判断token是否存在
     * @param token
     * @return
     */
    public static boolean jugdeTokenIsExist(String token){
        return token != null && !"".equals(token) && !"null".equals(token);
    }

    /**
     * 生成token
     * setIssuer() 签发者
     * setSubject() 面向用户
     * setAudience() 接收者
     * setExpiration() 签发时间
     * setNotBefore() 过期时间
     * setIssuedAt() 不能被接收处理时间，在此之前不能被接收处理
     * setId() JWT ID为web token提供唯一标识
     * @param subject
     * @return
     */
    public static String generateToken(String subject){
        String jwt = Jwts.builder()
                .setSubject(subject)
                .setIssuer("yk")
                //System.currentTimeMillis() + expireTime得到毫秒数
                .setExpiration(new Date(System.currentTimeMillis() + expireTime))
                .signWith(SignatureAlgorithm.HS512,secretKey)// 签名算法以及密匙
                .compact();
        return jwt;
    }

    /**
     * 生成带角色权限的token
     * @param subject
     * @param authorities
     * @return
     */
    public static String generateToken(String subject, Collection<? extends GrantedAuthority> authorities){

        StringBuilder stringBuilder = new StringBuilder();

        for(GrantedAuthority authority:authorities){
            stringBuilder.append(authority.getAuthority()).append(",");
        }
        String jwt = Jwts.builder()
                .setSubject(subject)
                .claim("authorities",stringBuilder)
                .setIssuer("yk")
                //System.currentTimeMillis() + expireTime得到毫秒数
                .setExpiration(new Date(System.currentTimeMillis() + expireTime))
                .signWith(SignatureAlgorithm.ES256,secretKey)// 签名算法以及密匙
                .compact();
        return jwt;
    }

    /**
     * 生成自定义时间的token
     * @param subject
     * @param expireTime
     * @return
     */
    public static String generateToken(String subject,long expireTime){
        String jwt = Jwts.builder()
                .setSubject(subject)
                .setIssuer("yk")
                //System.currentTimeMillis() + expireTime得到毫秒数
                .setExpiration(new Date(System.currentTimeMillis() + expireTime))
                .signWith(SignatureAlgorithm.ES256,secretKey)// 签名算法以及密匙
                .compact();
        return jwt;
    }

    /**
     * 得到tokenbody
     * 检验token是否有效
     * @param token
     * @return
     */
    public static Claims getTokenBody(String token){
        Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token.replace("Bearer","")).getBody();
        return claims;
    }

}
