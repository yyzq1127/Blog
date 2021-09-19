package com.yk.service;

import com.yk.config.RedisKeyConfig;
import com.yk.dao.FriendMapper;
import com.yk.entity.Friend;
import com.yk.utils.AssertUtil;
import com.yk.dao.SiteSettingMapper;
import com.yk.entity.SiteSetting;
import com.yk.model.dto.FriendInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author yk
 * @version 1.0
 * @date 2021/5/15 16:27
 */
@Service
public class FriendService {
    @Resource
    private FriendMapper friendMapper;

    @Resource
    private SiteSettingMapper siteSettingMapper;
    @Resource
    private RedisService redisService;
    /**
     * 查询友链
     * @return
     */
    public List<Friend> getFriendsByQuery() {
        return friendMapper.getFriendsByQuery();
    }

    /**
     * 更新公开状态
     * @param id
     * @param published
     * @return
     */
//    @Transactional
    public void updatePublished(long id, boolean published) {
        AssertUtil.isTrue(friendMapper.updatePublished(id,published)<1,"更新失败!");
        //更新缓存
        redisService.deleteCacheByKey(RedisKeyConfig.FRIEND_INFO_MAP);
    }

    /**
     * 添加友链
     * @param friend
     * @return
     */
//    @Transactional
    public void addFriend(Friend friend) {
        friend.setCreateTime(new Date());
        friend.setViews(0);
        AssertUtil.isTrue(friendMapper.addFriend(friend)<1,"添加失败!");
        //更新缓存
        redisService.deleteCacheByKey(RedisKeyConfig.FRIEND_INFO_MAP);
    }

    /**
     * 编辑友链
     * @param friend
     * @return
     */
//    @Transactional
    public void updateFriend(Friend friend) {
        AssertUtil.isTrue(friendMapper.updateFriend(friend)<1,"编辑失败!");
        //更新缓存
        redisService.deleteCacheByKey(RedisKeyConfig.FRIEND_INFO_MAP);
    }

    /**
     * 删除友链
     * @param id
     * @return
     */
//    @Transactional
    public void deleteFriendById(Long id) {
        AssertUtil.isTrue(friendMapper.deleteFriendById(id)<1,"删除失败!");
        //更新缓存
        redisService.deleteCacheByKey(RedisKeyConfig.FRIEND_INFO_MAP);
    }

    /**
     * 获取友链页面信息
     * @return
     */
    public FriendInfo getFriendInfo() {
        List<SiteSetting> siteSettings = siteSettingMapper.getFriendInfo();
        FriendInfo friendInfo = new FriendInfo();
        for(SiteSetting s:siteSettings){
            if("friendContent".equals(s.getNameEn())){
                friendInfo.setContent(s.getValue());
            } else {
                friendInfo.setCommentEnabled("1".equals(s.getValue()));
            }
        }
        return friendInfo;
    }

    /**
     * 编辑友链页面信息
     * @param friendInfo
     * @return
     */
//    @Transactional
    public void updateContent(FriendInfo friendInfo) {
        List<SiteSetting> siteSettings = siteSettingMapper.getFriendInfo();
        for(SiteSetting s:siteSettings){
            if("friendContent".equals(s.getNameEn())){
                s.setValue(friendInfo.getContent());
                AssertUtil.isTrue(siteSettingMapper.updateById(s)<1,"保存失败!");
                //更新缓存
                redisService.deleteCacheByKey(RedisKeyConfig.FRIEND_INFO_MAP);
                break;
            }
        }
    }

    /**
     * 页面评论开关
     * @param commentEnabled
     * @return
     */
//    @Transactional
    public void updateCommentEnabled(boolean commentEnabled) {
        List<SiteSetting> siteSettings = siteSettingMapper.getFriendInfo();
        String value;
        if(commentEnabled) {
            value = "1";
        } else {
            value = "0";
        }
        for(SiteSetting s:siteSettings){
            if("friendCommentEnabled".equals(s.getNameEn())){
                s.setValue(value);
                AssertUtil.isTrue(siteSettingMapper.updateById(s)<1,"操作失败!");
                //更新缓存
                redisService.deleteCacheByKey(RedisKeyConfig.FRIEND_INFO_MAP);
                break;
            }
        }


    }

    //查询评论开关
    public Boolean getCommentEnabled() {
        List<SiteSetting> siteSettingList = siteSettingMapper.getFriendInfo();
        for (SiteSetting siteSetting : siteSettingList) {
            if("friendCommentEnabled".equals(siteSetting.getNameEn())){
                return siteSetting.getValue().equals("1");
            }
        }
        return false;
    }
}
