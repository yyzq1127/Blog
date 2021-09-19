package com.yk.dao;

import com.yk.entity.Blog;
import com.yk.model.dto.*;

import java.util.List;
import java.util.Map;

public interface BlogMapper {


    List<Blog> getDataByQuery(String title, Integer categoryId);

    int deleteBlogById(long id);

    Blog selectBlogById(long id);

    Integer updateBlog(BlogInfo blogInfo);

    Integer addBlog(BlogInfo blogInfo);

    Integer updateTop(Long id, boolean top);

    Integer updateRecommend(Long id, boolean recommend);

    Integer updateVisibility(Visibility visibility, Long id);

    List<BlogList> queryBlogIdAndTitle();

    int getBlogCount();

    List<Map<String, Object>> getIdAndCount();

    List<BlogInfo> getBlogByIsPublished();

    List<RandomBlog> getRandomBlog(int randomBlogPageSize);

    List<NewBlog> getNewBlog(int newBlogPageSize);

    Blog getBlogById(Long id);

    Boolean getIsCommentEnabledById(Long blogId);

    List<Blog> getBlogByCategoryName(String categoryName);

    List<String> getDay();

    List<ArchiveBlog> getBLogByDayIsPublish(String s);

    Integer getCountIsPublish();

    String getPasswordById(Integer blogId);

    List<Blog> getBlogIsPublishById(List<Integer> ids);
}
