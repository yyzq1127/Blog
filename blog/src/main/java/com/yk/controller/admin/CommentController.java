package com.yk.controller.admin;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yk.model.ResultInfo;
import com.yk.service.BlogService;
import com.yk.service.CommentService;
import com.yk.annotation.OperationLogger;
import com.yk.model.dto.BlogList;
import com.yk.model.dto.CommentInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yk
 * @version 1.0
 * @date 2021/5/12 20:18
 */
@Controller
@RequestMapping("admin")
public class CommentController {
    @Resource
    private CommentService commentService;
    @Resource
    private BlogService blogService;

    /**
     * (分页)查询所有评论
     * @return
     */
    @RequestMapping("comments")
    @ResponseBody
    public ResultInfo comments(@RequestParam int pageNum, @RequestParam int pageSize){
        PageHelper.startPage(pageNum,pageSize);
        PageInfo<CommentInfo> pageInfo = new PageInfo<>(commentService.selectComments());
        Map<String,Object> map = new HashMap<>();
        map.put("list",pageInfo.getList());
        map.put("total",pageInfo.getTotal());
        return ResultInfo.createResult("查询成功!",map);
    }

    /**
     * 查询所有blogId和title
     */
    @GetMapping("blogIdAndTitle")
    @ResponseBody
    public ResultInfo blogIdAndTitle(){
        List<BlogList> list = new ArrayList<>();
        list = blogService.queryBlogIdAndTitle();
        return ResultInfo.createResult("查询成功!",list);
    }

    /**
     * 更新公开状态
     * @param id
     * @param published
     * @return
     */
    @PutMapping("comment/published")
    @ResponseBody
    @OperationLogger("更新公开状态")
    public ResultInfo updatePublished(@RequestParam long id,@RequestParam Boolean published){
        commentService.updatePublished(id,published);
        return ResultInfo.createResult("更新成功!");
    }

    /**
     * 更新邮件提醒状态
     * @param id
     * @param notice
     * @return
     */
    @PutMapping("comment/notice")
    @ResponseBody
    @OperationLogger("更新邮件提醒状态")
    public ResultInfo updateNotice(@RequestParam long id,@RequestParam Boolean notice){
        commentService.updateNotice(id,notice);
        return ResultInfo.createResult("更新成功!");
    }


    /**
     * 删除评论
     * 需要递归查询评论的子评论
     * @param id
     * @return
     */
    @DeleteMapping("comment")
    @ResponseBody
    @OperationLogger("删除评论")
    public ResultInfo deleteCommentById(@RequestParam long id){
        commentService.deleteCommentById(id);
        return ResultInfo.createResult("删除成功!");
    }

    /**
     * 编辑评论
     * @param commentInfo
     * @return
     */
    @PutMapping("comment")
    @ResponseBody
    @OperationLogger("编辑评论")
    public ResultInfo editComment(@RequestBody CommentInfo commentInfo){
        commentService.updateComment(commentInfo);
        return ResultInfo.createResult("编辑成功!");
    }
}
