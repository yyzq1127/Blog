package com.yk.controller;

import com.yk.config.RedisKeyConfig;
import com.yk.entity.Category;
import com.yk.entity.Tag;
import com.yk.model.ResultInfo;
import com.yk.model.dto.NewBlog;
import com.yk.model.dto.RandomBlog;
import com.yk.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yk
 * @version 1.0
 * @date 2021/6/10 21:26
 */
@Controller
public class SiteViewController {


    @Resource
    private SiteSettingService siteSettingService;
    @Resource
    private BlogService blogService;
    @Resource
    private CategoryService categoryService;
    @Resource
    private TagService tagService;
    @Resource
    private RedisService redisService;
    /**
     * 站点配置 博客推荐 分类列表 标签列表 随机博客
     * @return
     */
    @GetMapping("site")
    @ResponseBody
    public ResultInfo site(){
        String redisKey = RedisKeyConfig.SITE_INFO_MAP;
        Map map = redisService.getMapByKey(redisKey);
        if(map == null||map.size() == 0) {
            map = siteSettingService.getSiteInfo();
            List<NewBlog> newBlogs = blogService.getNewBlog();
            List<Category> categories = categoryService.selectCategories();
            List<Tag> tags = tagService.selectTagsNoId();
            List<RandomBlog> randomBlogs = blogService.getRandomBlog();
            map.put("categoryList", categories);
            map.put("tagList", tags);
            map.put("randomBlogList", randomBlogs);
            map.put("newBlogList", newBlogs);
            redisService.addMap(redisKey,map);
        }
        return ResultInfo.createResult("查询成功!",map);
    }
}
