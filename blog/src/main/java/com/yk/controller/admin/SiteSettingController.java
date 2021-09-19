package com.yk.controller.admin;

import com.yk.model.ResultInfo;
import com.yk.model.dto.SettingsAndDeleteIds;
import com.yk.service.SiteSettingService;
import com.yk.annotation.OperationLogger;
import com.yk.entity.SiteSetting;
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
 * @date 2021/5/15 9:43
 */
@RequestMapping("admin")
@Controller
public class SiteSettingController {
    @Resource
    private SiteSettingService siteSettingService;


    /**
     * 查询站点设置
     * @return
     */
    @GetMapping("siteSettings")
    @ResponseBody
    public ResultInfo getSiteSettingData(){
        List<SiteSetting> list = siteSettingService.getSiteSettingData();
        List<SiteSetting> list1 = new ArrayList<>();
        List<SiteSetting> list2 = new ArrayList<>();
        List<SiteSetting> list3 = new ArrayList<>();
        List<SiteSetting> list4 = new ArrayList<>();
        for(SiteSetting l:list){
            if(l.getType() == 1) {
                list1.add(l);
            } else if(l.getType() == 2) {
                list2.add(l);
            } else if(l.getType() == 3) {
                list3.add(l);
            } else if(l.getType() == 4) {
                list4.add(l);
            }
        }
        Map<String,Object> map = new HashMap<>();
        map.put("type1",list1);
        map.put("type2",list2);
        map.put("type3",list3);
        map.put("type4",list4);
        return ResultInfo.createResult("查询成功!",map);
    }


    /**
     * 更新站点信息
     * @param settingsAndDeleteIds
     * @return
     */
    @PostMapping("siteSettings")
    @ResponseBody
    @OperationLogger("更新站点信息")
    public ResultInfo update(@RequestBody SettingsAndDeleteIds settingsAndDeleteIds){
        siteSettingService.update(settingsAndDeleteIds.getSettings(),settingsAndDeleteIds.getDeleteIds());
        return ResultInfo.createResult("更新成功!");
    }



















}
