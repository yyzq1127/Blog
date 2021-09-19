package com.yk.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yk.entity.About;
import com.yk.entity.Comment;
import com.yk.entity.Friend;
import com.yk.model.ResultInfo;
import com.yk.model.dto.CommentInfo;
import com.yk.service.AboutService;
import com.yk.service.BlogService;
import com.yk.service.CommentService;
import com.yk.service.FriendService;
import com.yk.utils.AssertUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yk
 * @version 1.0
 * @date 2021/6/12 10:05
 */
@Controller
public class CommentViewController {


    @Resource
    private BlogService blogService;
    @Resource
    private AboutService aboutService;
    @Resource
    private FriendService friendService;
    @Resource
    private CommentService commentService;
    /**
     * page = (0 普通文章)(1 关于我)(2 友链)
     * @param page
     * @param blogId
     * @param pageNum
     * @param pageSize
     * @param jwt
     * @return
     */
    @GetMapping("comments")
    @ResponseBody
    public ResultInfo comments(@RequestParam Integer page,
                               @RequestParam(defaultValue = "") Long blogId,
                               @RequestParam(defaultValue = "1") Integer pageNum,
                               @RequestParam(defaultValue = "10") Integer pageSize,
                               @RequestHeader(value = "Authorization", defaultValue = "") String jwt){
        //1.参数判断
        AssertUtil.isTrue(!judgePageAndCommentEnabled(page,blogId),"评论已关闭");
        //2.登录判断

        //3.查询评论
        PageHelper.startPage(pageNum,pageSize);
        List<CommentInfo> list;
        if(page == 0){
            list = commentService.getCommentsById(blogId);
        } else {
            list = commentService.getCommentsByPage(page);
        }
        PageInfo<CommentInfo> pageInfo = new PageInfo<>(list);
        Map<String,Object> pageResult = new HashMap<>();
        pageResult.put("totalPage",pageInfo.getPages());
        pageResult.put("list",pageInfo.getList());
        Map<String,Object> map = new HashMap<>();
        map.put("count", pageInfo.getTotal());
        map.put("comments", pageResult);
        return ResultInfo.createResult("",map);
    }

    //判断是否允许评论
    //返回(true 允许评论)(false 评论未开启)
    private Boolean judgePageAndCommentEnabled(Integer page, Long blogId) {
        if(page == 0){//博客文章
            return blogService.getIsCommentEnabled(blogId);
        } else if(page == 1){//关于我页面
            return aboutService.getCommentEnabled();
        } else if(page == 2){//友链页面
            return friendService.getCommentEnabled();
        }
        return false;
    }
}
