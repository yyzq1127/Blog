package com.yk.service;

import com.yk.dao.BlogMapper;
import com.yk.dao.BlogTagMapper;
import com.yk.entity.Blog;
import com.yk.utils.AssertUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yk
 * @version 1.0
 * @date 2021/4/5 14:40
 */
@Service
public class BlogTagService {

    @Resource
    private BlogTagMapper blogTagMapper;
    @Resource
    private BlogMapper blogMapper;
    public void deleteBlogTagByBlogId(long id) {
        AssertUtil.isTrue(blogTagMapper.deleteBlogTagByBlogId(id) != 1,"博客标签删除失败!");
    }


    //通过tagName查询blogList
    public List<Blog> getBLogListIsPublishByTagName(String tagName) {
        List<Integer> ids = blogTagMapper.getBlogId(tagName);
        if(ids.size() == 0) {
            return new ArrayList<>();
        }
        return blogMapper.getBlogIsPublishById(ids);
    }
}
