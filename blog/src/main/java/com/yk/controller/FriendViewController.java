package com.yk.controller;

import com.yk.config.RedisKeyConfig;
import com.yk.entity.Friend;
import com.yk.model.ResultInfo;
import com.yk.model.dto.FriendInfo;
import com.yk.service.FriendService;
import com.yk.service.RedisService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yk
 * @version 1.0
 * @date 2021/6/13 10:12
 */
@Controller
public class FriendViewController {


    @Resource
    private FriendService friendService;
    @Resource
    private RedisService  redisService;

    @GetMapping("friends")
    @ResponseBody
    public ResultInfo friends(){
        String redisKey = RedisKeyConfig.FRIEND_INFO_MAP;
        Map map = redisService.getMapByKey(redisKey);
        if(map == null||map.isEmpty()){
            List<Friend> friendList = friendService.getFriendsByQuery();
            FriendInfo friendInfo = friendService.getFriendInfo();
            map = new HashMap<>(10);
            map.put("friendList",friendList);
            map.put("friendInfo",friendInfo);
            redisService.addMap(redisKey,map);
        }
        return ResultInfo.createResult("",map);
    }
}
