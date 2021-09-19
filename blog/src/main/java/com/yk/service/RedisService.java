package com.yk.service;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author yk
 * @version 1.0
 * @date 2021/6/6 15:55
 */
@Service
public class RedisService<T> {

    @Resource
    private RedisTemplate redisTemplate;

    //查List
    public  List<T> getListByKey(String redisKey) {
        List<T> redisResult = (List<T>) redisTemplate.opsForValue().get(redisKey);
        return redisResult;
    }
    //加List
    public void addList(String redisKey, List<T> list) {
        redisTemplate.opsForValue().set(redisKey,list);
    }
    //删除缓存
    public void deleteCacheByKey(String redisKey) {
        redisTemplate.delete(redisKey);
    }

    //查map
    public Map getMapByKey(String redisKey) {
        return redisTemplate.opsForHash().entries(redisKey);
    }
    //加map
    public void addMap(String redisKey, Map map) {
        redisTemplate.opsForHash().putAll(redisKey, map);
    }
    //查map(key-hash)
    public Map getMapByKeyAndHash(String redisKey, Integer pageNum) {
        return (Map)redisTemplate.opsForHash().get(redisKey,pageNum);
    }
    //加map(key-hash)
    public void addKeyAndValue(String redisKey, Integer pageNum, Map map) {
        redisTemplate.opsForHash().put(redisKey,pageNum,map);
    }
}
