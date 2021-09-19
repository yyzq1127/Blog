package com.yk.controller.admin;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yk.entity.Friend;
import com.yk.model.ResultInfo;
import com.yk.annotation.OperationLogger;
import com.yk.model.dto.FriendInfo;
import com.yk.service.FriendService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yk
 * @version 1.0
 * @date 2021/5/15 16:25
 */
@Controller
@RequestMapping("admin")
public class FriendController {
    @Resource
    private FriendService friendService;


    /**
     * 分页查询友链
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("friends")
    @ResponseBody
    public ResultInfo getFriendsByQuery(@RequestParam int pageNum, @RequestParam int pageSize){
        PageHelper.startPage(pageNum,pageSize);
        PageInfo<Friend> pageInfo = new PageInfo<>(friendService.getFriendsByQuery());
        Map<String,Object> map = new HashMap<>();
        map.put("list",pageInfo.getList());
        map.put("total",pageInfo.getTotal());
        return ResultInfo.createResult("查询成功!",map);
    }

    /**
     * 更新公开状态
     * @param id
     * @param published
     * @return
     */
    @PutMapping("friend/published")
    @ResponseBody
    @OperationLogger("更新公开状态")
    public ResultInfo updatePublished(@RequestParam long id,@RequestParam boolean published){
        friendService.updatePublished(id,published);
        return ResultInfo.createResult("更新成功!");
    }

    /**
     * 添加友链
     * @param friend
     * @return
     */
    @PostMapping("friend")
    @ResponseBody
    @OperationLogger("添加友链")
    public ResultInfo addFriend(@RequestBody  Friend friend){
        friendService.addFriend(friend);
        return ResultInfo.createResult("添加成功!");
    }

    /**
     * 编辑友链
     * @param friend
     * @return
     */
    @PutMapping("friend")
    @ResponseBody
    @OperationLogger("编辑友链")
    public ResultInfo updateFriend(@RequestBody Friend friend){
        friendService.updateFriend(friend);
        return ResultInfo.createResult("编辑成功!");
    }


    /**
     * 删除友链
     * @param id
     * @return
     */
    @DeleteMapping("friend")
    @ResponseBody
    @OperationLogger("删除友链")
    public ResultInfo deleteFriendById(@RequestParam Long id){
        friendService.deleteFriendById(id);
        return ResultInfo.createResult("删除成功!");
    }

    /**
     * 获取友链页面信息
     * (站点设置表中type==4)
     * @return
     */
    @GetMapping("friendInfo")
    @ResponseBody
    public ResultInfo getFriendInfo(){
        return ResultInfo.createResult("请求成功!",friendService.getFriendInfo());
    }


    /**
     * 编辑友链页面信息
     * @param friendInfo
     * @return
     */
    @PutMapping("friendInfo/content")
    @ResponseBody
    @OperationLogger("编辑友链页面信息")
    public ResultInfo updateContent(@RequestBody FriendInfo friendInfo){
        friendService.updateContent(friendInfo);
        return ResultInfo.createResult("保存成功!");
    }


    /**
     * 页面评论开关
     * @param commentEnabled
     * @return
     */
    @PutMapping("friendInfo/commentEnabled")
    @ResponseBody
    @OperationLogger("友链页面评论开关")
    public ResultInfo updateCommentEnabled(@RequestParam boolean commentEnabled){
        friendService.updateCommentEnabled(commentEnabled);
        return ResultInfo.createResult("操作成功!");
    }






}
