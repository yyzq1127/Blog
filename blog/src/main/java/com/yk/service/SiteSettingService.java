package com.yk.service;

import com.alibaba.fastjson.JSON;
import com.yk.config.RedisKeyConfig;
import com.yk.model.dto.Introduction;
import com.yk.utils.AssertUtil;
import com.yk.dao.SiteSettingMapper;
import com.yk.entity.SiteSetting;
import com.yk.utils.JacksonUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author yk
 * @version 1.0
 * @date 2021/5/15 9:44
 */
@Service
public class SiteSettingService {
    @Resource
    private SiteSettingMapper siteSettingMapper;
    @Resource
    private RedisService redisService;

    private static final Pattern PATTERN = Pattern.compile("\"(.*?)\"");
    /**
     * 查询站点设置
     * @return
     */
    public List<SiteSetting> getSiteSettingData() {
        return siteSettingMapper.getSiteSettingData();
    }

    /**
     * 更新站点设置
     * @param settings
     * @param deleteIds
     */
    public void update(List<SiteSetting> settings, Long[] deleteIds) {
        for(SiteSetting siteSetting:settings){
            if(siteSetting.getId()!=null)
                //修改
            {
                AssertUtil.isTrue(siteSettingMapper.updateById(siteSetting)<1,"更新失败!");
            } else {
                AssertUtil.isTrue(siteSettingMapper.addSetting(siteSetting)<1,"添加失败!");
            }
        }
        if(deleteIds.length==0) {
            return;
        }
        //批量删除
        AssertUtil.isTrue(siteSettingMapper.deleteByIds(deleteIds)<1,"删除失败!");
        //更新缓存
        redisService.deleteCacheByKey(RedisKeyConfig.SITE_INFO_MAP);
    }

    /**
     * 首页站点信息查询
     * @return
     */
    public Map<String, Object> getSiteInfo() {
        List<SiteSetting> siteSettingList = siteSettingMapper.getSiteSettingData();
        Map<String,Object> map = new HashMap<>();
        Map<String,Object> siteInfo = new HashMap<>();
        Map<String,Object> introduction = new HashMap<>();
//        Introduction introduction = new Introduction();
        Map<String,Object> copyright;
        List<Map<String,Object>> badges = new ArrayList<>();
        List<Map<String,Object>> favorites = new ArrayList<>();
        List<String> rollTexts = new ArrayList<>();
        for(SiteSetting s:siteSettingList){
            if(s.getType() == 1){
                if(s.getNameEn().equals("copyright")){
                    //Copyright copyright = JacksonUtils.readValue(s.getValue(), Copyright.class);
                    //					siteInfo.put(s.getNameEn(), copyright);
                    //字符串转JSON
                    copyright = (Map)JSON.parse(s.getValue());
                    siteInfo.put("copyright",copyright);
                }else {
                    siteInfo.put(s.getNameEn(), s.getValue());
                }
            } else if(s.getType() == 2){
//                Badge badge = JacksonUtil.readValue(s.getValue(), Badge.class);
//                badges.add(badge);
                  Map<String,Object> badge = (Map<String,Object>)JSON.parse(s.getValue());
                  badges.add(badge);
            } else if(s.getType() == 3){
                if(s.getNameEn().equals("favorite")){
//                    Favorite favorite = JacksonUtils.readValue(s.getValue(), Favorite.class);
//                    favorites.add(favorite);
                    Map<String,Object> favorite = (Map<String,Object>)JSON.parse(s.getValue());
                    favorites.add(favorite);
                } else if(s.getNameEn().equals("rollText")){
                    //正则表达式
                    Matcher m = PATTERN.matcher(s.getValue());
                    while (m.find()) {
                        rollTexts.add(m.group(1));
                    }
                } else {
                    introduction.put(s.getNameEn(),s.getValue());
//                    if ("avatar".equals(s.getNameEn())) {
//                        introduction.setAvatar(s.getValue());
//                    } else if ("name".equals(s.getNameEn())) {
//                        introduction.setName(s.getValue());
//                    } else if ("github".equals(s.getNameEn())) {
//                        introduction.setGithub(s.getValue());
//                    } else if ("qq".equals(s.getNameEn())) {
//                        introduction.setQq(s.getValue());
//                    } else if ("bilibili".equals(s.getNameEn())) {
//                        introduction.setBilibili(s.getValue());
//                    } else if ("netease".equals(s.getNameEn())) {
//                        introduction.setNetease(s.getValue());
//                    } else if ("email".equals(s.getNameEn())) {
//                        introduction.setEmail(s.getValue());
//                    }

                }
            }
        }
        introduction.put("rollText",rollTexts);
        introduction.put("favorites",favorites);
//        introduction.setRollText(rollTexts);
//        introduction.setFavorites(favorites);
        map.put("siteInfo",siteInfo);
        map.put("introduction",introduction);
        map.put("badges", badges);
        return map;
    }
}
