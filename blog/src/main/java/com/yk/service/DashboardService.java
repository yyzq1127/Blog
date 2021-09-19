package com.yk.service;

import com.yk.dao.*;
import com.yk.entity.Category;
import com.yk.entity.CityVisitor;
import com.yk.entity.Tag;
import com.yk.entity.VisitRecord;
import com.yk.model.dto.TagBlogCount;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author yk
 * @version 1.0
 * @date 2021/5/17 15:12
 */
@Service
public class DashboardService {

    @Resource
    private VisitLogMapper visitLogMapper;
    @Resource
    private BlogMapper blogMapper;
    @Resource
    private CommentMapper  commentMapper;
    @Resource
    private CityVisitorMapper cityVisitorMapper;
    @Resource
    private CategoryMapper categoryMapper;
    @Resource
    private TagMapper tagMapper;
    @Resource
    private VisitRecordMapper visitRecordMapper;
    //查询今日浏览次数
    public int countVisitLogByToday() {
        return visitLogMapper.countVisitLogByToday(new Date());
    }

    //查询blog数量
    public int getBlogCount() {
        return blogMapper.getBlogCount();
    }
    //查询评论数量
    public int getCommentCount() {
        return commentMapper.getCommentCount();
    }
    //访客地图
    public List<CityVisitor> getCityVisitorList() {
        return cityVisitorMapper.getCityVisitorList();
    }

    //文章分类 和  对应文章数
    public Map<String, List> getCategoryBlogCountMap() {
        //储存所有分类名
        List<String> legend = new ArrayList<>();
        //储存分类名和对应blog数量
        List<Map<String,Object>> series = new ArrayList<>();

        //查询所有分类
        List<Category> categories = categoryMapper.selectCategories();

        Map<Long,String> idAndName = new HashMap<>();

        for(Category c:categories){
            legend.add(c.getName());
            idAndName.put(c.getId(),c.getName());
        }
        //查询分类id及其对应blog数量
        List<Map<String,Object>> idAndCount = blogMapper.getIdAndCount();
        for(Map<String,Object> i:idAndCount){
            Map<String,Object> map = new HashMap<>();
            map.put("name",idAndName.get(i.get("id")));
            map.put("value",i.get("counts"));
            series.add(map);
        }
        Map<String, List> map = new HashMap<>();
        map.put("legend", legend);
        map.put("series", series);
        return map;
    }
    //标签分类 和 对应文章数
    public Map<String, List> getTagBlogCountMap() {
        //查询标签id对应的博客数量
        List<TagBlogCount> tagBlogCountList = tagMapper.getTagBlogCount();
        //查询所有标签的id和名称
        List<Tag> tagList = tagMapper.getTagList();
        //所有标签名称的List
        List<String> legend = new ArrayList<>();
        for (Tag tag : tagList) {
            legend.add(tag.getName());
        }
        //标签对应的博客数量List
        List<TagBlogCount> series = new ArrayList<>();
        Map<Long, String> m = new HashMap<>();
        for (Tag t : tagList) {
            m.put(t.getId(), t.getName());
        }
        for (TagBlogCount t : tagBlogCountList) {
            t.setName(m.get(t.getId()));
            series.add(t);
        }

        Map<String, List> map = new HashMap<>();
        map.put("legend", legend);
        map.put("series", series);
        return map;
    }

    //查询VisitRecord
    public Map<String, List> getVisitRecordMap() {
        List<String> data = new ArrayList<>();
        List<Integer> uv = new ArrayList<>();
        List<Integer> pv = new ArrayList<>();
        List<VisitRecord> visitRecords = visitRecordMapper.getVisitRecords();
        for(VisitRecord v:visitRecords){
            data.add(v.getDate());
            uv.add(v.getUv());
            pv.add(v.getPv());
        }
        Map<String, List> map = new HashMap<>();
        map.put("data",data);
        map.put("uv",uv);
        map.put("pv",pv);
        return map;
    }
}
