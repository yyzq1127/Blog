package com.yk.controller;

import com.github.pagehelper.PageHelper;
import com.yk.config.RedisKeyConfig;
import com.yk.model.ResultInfo;
import com.yk.service.BlogService;
import com.yk.service.RedisService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yk
 * @version 1.0
 * @date 2021/6/13 15:47
 */
@Controller
public class ArchiveViewController {

    @Resource
    private BlogService blogService;
    @Resource
    private RedisService redisService;

    /**
     * map<日期,对应blog列表>
     * @return
     */
    @GetMapping("archives")
    @ResponseBody
    public ResultInfo archives(){
        String redisKey = RedisKeyConfig.ARCHIVE_BLOG_MAP;
        Map map = redisService.getMapByKey(redisKey);
        if (map == null||map.isEmpty()) {
            map = blogService.getArchiveBlogIsPublish();
            Integer count = blogService.getCountIsPublish();
            map.put("count",count);
            redisService.addMap(redisKey,map);
        }
        return ResultInfo.createResult("",map);
    }
}
