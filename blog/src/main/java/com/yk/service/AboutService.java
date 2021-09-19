package com.yk.service;

import com.yk.config.RedisKeyConfig;
import com.yk.dao.AboutMapper;
import com.yk.entity.About;
import com.yk.utils.AssertUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yk
 * @version 1.0
 * @date 2021/5/16 16:09
 */
@Service
public class AboutService {

    @Resource
    private AboutMapper aboutMapper;
    @Resource
    private RedisService redisService;
    /**
     * 查询about页面
     */
    public Map<String,Object> getAbout() {
        String redisKey = RedisKeyConfig.ABOUT_INFO_MAP;
        Map map = redisService.getMapByKey(redisKey);
        if(map == null||map.size() == 0) {
            map = new HashMap<>(10);
            List<About> abouts = aboutMapper.getAbout();
            for (About about : abouts) {
                if ("title".equals(about.getNameEn())) {
                    map.put("title", about.getValue());
                } else if ("musicId".equals(about.getNameEn())) {
                    map.put("musicId", about.getValue());
                } else if ("content".equals(about.getNameEn())) {
                    map.put("content", about.getValue());
                } else if ("commentEnabled".equals(about.getNameEn())) {
                    map.put("commentEnabled", about.getValue());
                }
            }
            redisService.addMap(redisKey,map);
        }
        return map;
    }

    /**
     * 编辑about页面
     * @param map
     * @return
     */
    public void updateAbout(Map<String, Object> map) {
        List<About> abouts = aboutMapper.getAbout();
        for(About about:abouts){
            if("title".equals(about.getNameEn())) {
                about.setValue(map.get("title").toString());
            } else if("musicId".equals(about.getNameEn())) {
                about.setValue(map.get("musicId").toString());
            } else if("content".equals(about.getNameEn())) {
                about.setValue(map.get("content").toString());
            } else if("commentEnabled".equals(about.getNameEn())){
                if(map.get("commentEnabled").toString().equals("true")) {
                    about.setValue("true");
                } else {
                    about.setValue("false");
                }
            }
            AssertUtil.isTrue(aboutMapper.updateAbout(about)<1,"保存失败!");
        }
        redisService.deleteCacheByKey(RedisKeyConfig.ABOUT_INFO_MAP);
    }

    //查询评论开关
    public Boolean getCommentEnabled() {
        List<About> aboutList = aboutMapper.getAbout();
        for (About about : aboutList) {
            if(about.getNameEn().equals("commentEnabled")){
                return about.getValue().equals("true");
            }
        }
        return false;
    }
}
