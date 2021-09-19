package com.yk.controller.admin;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yk.entity.ScheduleJob;
import com.yk.model.dto.ScheduleJobInfo;
import com.yk.annotation.OperationLogger;
import com.yk.model.ResultInfo;
import com.yk.service.ScheduleJobService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yk
 * @version 1.0
 * @date 2021/5/29 15:22
 */
@Controller
@RequestMapping("admin")
public class ScheduleJobController {
    @Resource
    private ScheduleJobService scheduleJobService;


    @GetMapping("jobs")
    @ResponseBody
    public ResultInfo getJobList(@RequestParam Integer pageNum,
                                 @RequestParam Integer pageSize){
        PageHelper.startPage(pageNum,pageSize);
        PageInfo<ScheduleJob> pageInfo = new PageInfo<>(scheduleJobService.getJobList());
        Map<String,Object> map = new HashMap<>();
        map.put("list",pageInfo.getList());
        map.put("total",pageInfo.getTotal());
        return ResultInfo.createResult("查询成功!",map);
    }

    /**
     * 根据jobId更新status
     * @param jobId
     * @param status
     * @return
     */
    @ResponseBody
    @PutMapping("job/status")
    public ResultInfo updateJobStatus(@RequestParam Long jobId,
                                      @RequestParam Boolean status){
        Byte s=0;
        if(status) {
            s=1;
        }
        scheduleJobService.updateJobStatus(jobId,s);
        return ResultInfo.createResult("更新成功!");
    }

    /**
     * 添加操作
     * @param scheduleJob
     * @return
     */
    @ResponseBody
    @PostMapping("job")
    @OperationLogger("添加任务")
    public ResultInfo addJob(@RequestBody ScheduleJob scheduleJob){
        scheduleJobService.addJob(scheduleJob);
        return ResultInfo.createResult("添加成功!");
    }


    /**
     * 编辑操作
     * @param scheduleJobInfo
     * @return
     */
    @ResponseBody
    @PutMapping("job")
    @OperationLogger("编辑任务")
    public ResultInfo editJob(@RequestBody ScheduleJobInfo scheduleJobInfo){
        scheduleJobService.editJob(scheduleJobInfo);
        return ResultInfo.createResult("编辑成功!");
    }


    /**
     * 删除
     * @param jobId
     * @return
     */
    @ResponseBody
    @DeleteMapping("job")
    @OperationLogger("删除任务")
    public ResultInfo delete(@RequestParam Long jobId){
        scheduleJobService.deleteById(jobId);
        return ResultInfo.createResult("删除成功!");
    }








    @ResponseBody
    @PostMapping("job/run")
    @OperationLogger("执行任务")
    public ResultInfo runJobOnce(@RequestParam Long jobId){
        return ResultInfo.createResult("执行成功!");
    }


}
