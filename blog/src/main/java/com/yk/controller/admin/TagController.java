package com.yk.controller.admin;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yk.annotation.OperationLogger;
import com.yk.entity.Tag;
import com.yk.model.Page;
import com.yk.model.ResultInfo;
import com.yk.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yk
 * @version 1.0
 * @date 2021/4/14 21:21
 */
@Controller
@RequestMapping("admin")
public class TagController {
    @Autowired
    private TagService tagService;


    /**
     * 查询所有tag
     * @return
     */
    @GetMapping("tags")
    @ResponseBody
    public ResultInfo queryTags(){
        PageHelper.startPage(Page.getPageNumber(),Page.getPageSize());
        PageInfo<Tag> pageInfo = new PageInfo<>(tagService.selectTags());
        Map<String,Object> map = new HashMap<>();
        map.put("list",pageInfo.getList());
        map.put("total",pageInfo.getTotal());
        return ResultInfo.createResult("查询成功!",map);
    }

    /**
     * 添加标签
     * @param tag
     * @return
     */
    @PostMapping("tag")
    @ResponseBody
    @OperationLogger("添加标签")
    public ResultInfo addTag(@RequestBody Tag tag){
        return tagService.addTag(tag);
    }

    /**
     * 更新标签
     * @param tag
     * @return
     */
    @PutMapping("tag")
    @ResponseBody
    @OperationLogger("更新标签")
    public ResultInfo updateTag(@RequestBody Tag tag){
        return tagService.updateTag(tag);
    }

    /**
     * 删除标签
     * @param id
     * @return
     */
    @DeleteMapping("tag")
    @ResponseBody
    @OperationLogger("删除标签")
    public ResultInfo deleteTag(@RequestParam Long id){
        return tagService.deleteTag(id);
    }
}
