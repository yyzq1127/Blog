package com.yk.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yk.entity.Blog;
import com.yk.model.ResultInfo;
import com.yk.service.BlogTagService;
import com.yk.service.TagService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yk
 * @version 1.0
 * @date 2021/6/13 21:33
 */
@Controller
public class TagVIewController {

    @Resource
    private BlogTagService blogTagService;

    @GetMapping("tag")
    @ResponseBody
    public ResultInfo tag(@RequestParam String tagName){
        PageHelper.startPage(1,20);
        PageInfo<Blog> pageInfo = new PageInfo<>(blogTagService.getBLogListIsPublishByTagName(tagName));
        Map<String,Object> map = new HashMap<>(10);

        map.put("list",pageInfo.getList());
        map.put("totalPage",pageInfo.getPages());
        return ResultInfo.createResult("",map);

    }
}
