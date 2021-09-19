package com.yk.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yk.config.RedisKeyConfig;
import com.yk.entity.Blog;
import com.yk.model.ResultInfo;
import com.yk.model.dto.BlogInfo;
import com.yk.service.BlogService;
import com.yk.service.RedisService;
import com.yk.utils.AssertUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yk
 * @version 1.0
 * @date 2021/6/10 19:33
 */
@Controller
public class BlogViewController {


    @Resource
    private BlogService blogService;
    @Resource
    private RedisService redisService;

    //每页显示5条博客简介
    private static final int pageSize = 5;
    //博客简介列表排序方式     "必须使用数据库字段"
    private static final String orderBy = "is_top desc, create_time desc";


    @GetMapping("blogs")
    @ResponseBody
    public ResultInfo getBlogByIsPublished(@RequestParam(defaultValue = "1") Integer pageNum){
        String redisKey = RedisKeyConfig.HOME_BLOG_INFO_LIST;
        Map map = redisService.getMapByKeyAndHash(redisKey,pageNum);
        List<BlogInfo> list;
//        Map<String, Object>map = new HashMap<>(10);
        if(map == null || map.size() == 0) {
            map = new HashMap();
            //PageHelper的order by方法可替代mybatis中order by
            //避免SQL注入
            PageHelper.startPage(pageNum, pageSize, orderBy);
            list = blogService.getBlogByIsPublished();
            PageInfo<BlogInfo> pageInfo = new PageInfo<>(list);
            map.put("list",pageInfo.getList());
            map.put("totalPage",pageInfo.getPages());
            redisService.addKeyAndValue(redisKey,pageNum,map);
        }
        return ResultInfo.createResult("查询成功!",map);
    }

    @GetMapping("blog")
    @ResponseBody
    public ResultInfo getBlogById(@RequestParam Long id){
        Blog blog = blogService.getBlogById(id);
        return ResultInfo.createResult("",blog);
    }


    //密码校验
    @PostMapping("checkBlogPassword")
    @ResponseBody
    public ResultInfo checkBlogPassword(@RequestBody Map<String,Object> blog){
        String pwd = blogService.getPasswordById(Integer.valueOf(blog.get("blogId").toString()));
        AssertUtil.isTrue(!blog.get("password").toString().equals(pwd),"密码错误");

        return ResultInfo.createResult("");
    }
}
