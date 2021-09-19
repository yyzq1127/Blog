package com.yk.dao;

import com.yk.entity.SiteSetting;

import java.util.List;

public interface SiteSettingMapper {

    List<SiteSetting> getSiteSettingData();

    Integer updateById(SiteSetting siteSetting);

    Integer deleteByIds(Long[] deleteIds);

    Integer addSetting(SiteSetting siteSetting);

    List<SiteSetting> getFriendInfo();
}
