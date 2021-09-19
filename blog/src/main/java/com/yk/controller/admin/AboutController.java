package com.yk.controller.admin;

import com.yk.model.ResultInfo;
import com.yk.annotation.OperationLogger;
import com.yk.service.AboutService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author yk
 * @version 1.0
 * @date 2021/5/16 16:09
 */
@Controller
@RequestMapping("admin")
public class AboutController {

    @Resource
    private AboutService aboutService;


    /**
     * 查询about页面
     * @return
     */
    @GetMapping("about")
    @ResponseBody
    public ResultInfo getAbout(){
        return ResultInfo.createResult("查询成功!",aboutService.getAbout());
    }

    /**
     * 编辑about页面
     * @param map
     * @return
     */
    @PutMapping("about")
    @ResponseBody
    @OperationLogger("编辑about页面")
    public ResultInfo updateAbout(@RequestBody Map<String,Object>  map){
        aboutService.updateAbout(map);
        return ResultInfo.createResult("保存成功!");
    }
}
