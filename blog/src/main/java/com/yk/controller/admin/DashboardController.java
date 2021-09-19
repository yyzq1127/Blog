package com.yk.controller.admin;

import com.yk.entity.CityVisitor;
import com.yk.model.ResultInfo;
import com.yk.service.DashboardService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * dashboard
 * @author yk
 * @version 1.0
 * @date 2021/4/4 15:27
 */
@Controller
@RequestMapping("admin")
public class DashboardController {

    @Resource
    private DashboardService dashboardService;


    @GetMapping("dashboard")
    @ResponseBody
    public ResultInfo dashboard(){
        //查询今日浏览次数
        int todayPV = dashboardService.countVisitLogByToday();
        //int todayUV = redisService.countBySet(RedisKeyConfig.IDENTIFICATION_SET);
        //查询blog数量
        int blogCount = dashboardService.getBlogCount();
        int commentCount = dashboardService.getCommentCount();
        Map<String, List> categoryBlogCountMap = dashboardService.getCategoryBlogCountMap();
        Map<String, List> tagBlogCountMap = dashboardService.getTagBlogCountMap();
        Map<String, List> visitRecordMap = dashboardService.getVisitRecordMap();
        List<CityVisitor> cityVisitorList = dashboardService.getCityVisitorList();

        Map<String, Object> map = new HashMap<>();
        map.put("pv", todayPV);
        map.put("uv", 0);
        map.put("blogCount", blogCount);
        map.put("commentCount", commentCount);
        map.put("category", categoryBlogCountMap);
        map.put("tag", tagBlogCountMap);
        map.put("visitRecord", visitRecordMap);
        map.put("cityVisitor", cityVisitorList);
        return ResultInfo.createResult("请求成功!",map);
    }





}
