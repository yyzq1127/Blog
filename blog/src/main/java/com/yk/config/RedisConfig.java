package com.yk.config;

import com.alibaba.fastjson.support.spring.GenericFastJsonRedisSerializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;

/**
 * @author yk
 * @version 1.0
 * @date 2021/6/6 17:03
 */
@Configuration
public class RedisConfig {


     /*
     * Redis默认序列化
     *
     * @param redisConnectionFactory
     * @return
     */
    @Bean
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        // 使用 GenericFastJsonRedisSerializer 替换默认序列化
        GenericFastJsonRedisSerializer genericFastJsonRedisSerializer = new GenericFastJsonRedisSerializer();
        // 设置key和value的序列化规则
        redisTemplate.setKeySerializer(new GenericToStringSerializer<>(Object.class));
        redisTemplate.setValueSerializer(genericFastJsonRedisSerializer);
        // 设置hashKey和hashValue的序列化规则
        redisTemplate.setHashKeySerializer(new GenericToStringSerializer<>(Object.class));
        redisTemplate.setHashValueSerializer(genericFastJsonRedisSerializer);
        // 设置支持事物
        //redisTemplate.setEnableTransactionSupport(true);
        //redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }
}
