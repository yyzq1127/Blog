package com.yk.controller.admin;

import com.yk.annotation.OperationLogger;
import com.yk.entity.Moment;
import com.yk.model.ResultInfo;
import com.yk.service.MomentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author yk
 * @version 1.0
 * @date 2021/4/11 16:18
 */
@Controller
@RequestMapping("admin")
public class MomentController {
    @Autowired
    private MomentService momentService;


    /**
     * 查询动态
     * @return
     */
    @GetMapping("moments")
    @ResponseBody
    public ResultInfo selectMoments(){
        return momentService.selectMoments();
    }

    /**
     * 更新发布状态
     * @param id
     * @param published
     * @return
     */
    @PutMapping("moment/published")
    @ResponseBody
    @OperationLogger("更新发布状态")
    public ResultInfo updatePublished(@RequestParam Long id, @RequestParam boolean published){
        return momentService.updatePublished(id,published);
    }

    /**
     * 通过id查询动态
     * @param id
     * @return
     */
    @GetMapping("moment")
    @ResponseBody
    public ResultInfo getMomentById(Long id){
        return momentService.getMomentById(id);
    }

    /**
     * 通过id删除动态
     * @param id
     * @return
     */
    @DeleteMapping("moment")
    @ResponseBody
    @OperationLogger("删除动态")
    public ResultInfo deleteMomentById(Long id){
        return momentService.deleteMomentById(id);
    }

    /**
     * 添加动态
     * @param moment
     * @return
     */
    @PostMapping("moment")
    @ResponseBody
    @OperationLogger("添加动态")
    public ResultInfo addMoment(@RequestBody Moment moment){
        return momentService.addMoment(moment);
    }

    /**
     * 更新动态
     * @param moment
     * @return
     */
    @PutMapping("moment")
    @ResponseBody
    @OperationLogger("更新动态")
    public ResultInfo updateMoment(@RequestBody Moment moment){
        return momentService.updateMoment(moment);
    }
}
