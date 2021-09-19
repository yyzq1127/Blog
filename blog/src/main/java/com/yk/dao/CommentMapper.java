package com.yk.dao;


import com.yk.entity.Comment;
import com.yk.model.dto.CommentInfo;

import java.util.List;

public interface CommentMapper {

    int deleteCommentByBlogId(long id);

    List<CommentInfo> selectComments();

    Comment selectById(long id);

    Integer updatePublished(long id, Boolean published);

    Integer updateNotice(long id, Boolean notice);

    Integer deleteCommentById(long id);

    Integer updateCommentById(CommentInfo commentInfo);

    int getCommentCount();

    List<CommentInfo> getCommentsById(Long blogId);

    List<CommentInfo> getCommentsByPage(Integer page);
}
