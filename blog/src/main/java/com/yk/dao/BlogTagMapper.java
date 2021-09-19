package com.yk.dao;


import com.yk.entity.Blog;

import java.util.List;

public interface BlogTagMapper {

    int deleteBlogTagByBlogId(long id);

    Integer addBlogTag(Long blogId, Long tagId);

    List<Integer> getBlogId(String tagName);

    List<Blog> getBlogIsPublishById(List<Integer> ids);
}
