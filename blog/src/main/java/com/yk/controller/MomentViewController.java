package com.yk.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yk.entity.Moment;
import com.yk.model.ResultInfo;
import com.yk.service.MomentService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yk
 * @version 1.0
 * @date 2021/6/13 10:35
 */
@Controller
public class MomentViewController {
    @Resource
    private MomentService momentService;

    @GetMapping("moments")
    @ResponseBody
    public ResultInfo moments(@RequestParam Integer pageNum){
        PageHelper.startPage(pageNum,3);
        PageInfo<Moment> pageInfo = new PageInfo<>(momentService.getMoments());
        Map<String,Object> map = new HashMap<>();
        map.put("list",pageInfo.getList());
        map.put("totalPage",pageInfo.getPages());
        return ResultInfo.createResult("",map);
    }

    @PostMapping("moment/like")
    @ResponseBody
    public ResultInfo like(@RequestParam Long id){
        momentService.like(id);
        return ResultInfo.createResult("点赞成功");
    }
}
