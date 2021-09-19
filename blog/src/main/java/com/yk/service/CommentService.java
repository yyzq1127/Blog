package com.yk.service;

import com.yk.utils.AssertUtil;
import com.yk.dao.CommentMapper;
import com.yk.entity.Comment;
import com.yk.model.dto.CommentInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author yk
 * @version 1.0
 * @date 2021/4/5 14:41
 */
@Service
public class CommentService {


    @Resource
    private CommentMapper commentMapper;

    /**
     * 评论删除
     * @param id
     */
    @Transactional
    public void deleteCommentByBlogId(long id) {
        //评论不一定存在
        commentMapper.deleteCommentByBlogId(id);
    }

    /**
     * 评论查询
     * @return
     */
    public List<CommentInfo> selectComments() {
        return commentMapper.selectComments();
    }

    /**
     * 更新发布状态
     * @param id
     * @param published
     */
    @Transactional
    public void updatePublished(long id, Boolean published) {
        //判断评论是否存在
        Comment comment = commentMapper.selectById(id);
        AssertUtil.isTrue(comment == null,"待更新评论不存在!");
        AssertUtil.isTrue(commentMapper.updatePublished(id,published) < 1,"更新失败!");
    }

    /**
     * 更新邮件提醒状态
     * @param id
     * @param notice
     */
    @Transactional
    public void updateNotice(long id, Boolean notice) {
        Comment comment = commentMapper.selectById(id);
        AssertUtil.isTrue(comment == null,"待更新评论不存在!");
        AssertUtil.isTrue(commentMapper.updateNotice(id,notice) < 1,"更新失败!");
    }

    /**
     * 删除评论
     * @param id
     * @return
     */
    @Transactional
    public void deleteCommentById(long id) {
        //判断评论是否存在
        Comment comment = commentMapper.selectById(id);
        AssertUtil.isTrue(comment == null,"待删除评论不存在!");
        AssertUtil.isTrue(commentMapper.deleteCommentById(id)<1,"删除失败");
    }

    /**
     * 编辑评论
     * @param commentInfo
     * @return
     */
    @Transactional
    public void updateComment(CommentInfo commentInfo) {
        //判断评论是否存在
        Comment comment = commentMapper.selectById(commentInfo.getId());
        AssertUtil.isTrue(comment == null,"待删除评论不存在!");
        AssertUtil.isTrue(commentMapper.updateCommentById(commentInfo)<1,"编辑失败!");
    }

    //通过博客id查询评论
    public List<CommentInfo> getCommentsById(Long blogId) {
        return commentMapper.getCommentsById(blogId);
    }

    //通过特定页面查询评论
    public List<CommentInfo> getCommentsByPage(Integer page) {
        return commentMapper.getCommentsByPage(page);
    }
}
