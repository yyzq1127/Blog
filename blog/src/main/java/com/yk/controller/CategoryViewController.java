package com.yk.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yk.entity.Blog;
import com.yk.model.ResultInfo;
import com.yk.service.BlogService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yk
 * @version 1.0
 * @date 2021/6/12 17:03
 */
@Controller
public class CategoryViewController {

    @Resource
    private BlogService blogService;

    @GetMapping("category")
    @ResponseBody
    public ResultInfo getBlogByCategoryName(@RequestParam String categoryName,
                                            @RequestParam(defaultValue = "1") Integer pageNum){
        PageHelper.startPage(pageNum,5);
        PageInfo<Blog> pageInfo = new PageInfo<>(blogService.getBlogByCategoryName(categoryName));
        Map<String,Object> map = new HashMap<>(10);
        map.put("list",pageInfo.getList());
        map.put("totalPage",pageInfo.getPages());
        return ResultInfo.createResult("",map);
    }
}
