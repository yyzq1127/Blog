package com.yk.controller;

import com.yk.model.ResultInfo;
import com.yk.service.AboutService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author yk
 * @version 1.0
 * @date 2021/6/13 9:48
 */
@Controller
public class AboutViewController {

    @Resource
    private AboutService aboutService;


    @GetMapping("about")
    @ResponseBody
    public ResultInfo about(){
        Map<String, Object> about = aboutService.getAbout();
        return ResultInfo.createResult("",about);
    }
}
