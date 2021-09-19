package com.yk.controller.admin;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yk.annotation.OperationLogger;
import com.yk.entity.VisitLog;
import com.yk.model.ResultInfo;
import com.yk.service.VisitLogService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yk
 * @version 1.0
 * @date 2021/5/26 21:55
 */
@Controller
@RequestMapping("admin")
public class VisitLogController {
    @Resource
    private VisitLogService visitLogService;

    @GetMapping("visitLogs")
    @ResponseBody
    public ResultInfo getVisitLogs(@RequestParam(defaultValue = "") String uuid,
                                 @RequestParam(defaultValue = "") String date,
                                 @RequestParam Integer pageNum,
                                 @RequestParam Integer pageSize) throws ParseException {

        //字符串转日期
        Date date1;
        Date date2;
        if("".equals(date)){
            date1 = null;
            date2 = null;
        } else {
            String[]  dates=date.split(",");
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            date1 = format.parse(dates[0]);
            date2 = format.parse(dates[1]);
        }
        PageHelper.startPage(pageNum,pageSize);
        PageInfo<VisitLog> pageInfo = new PageInfo<>(visitLogService.getVisitLogs(uuid,date1,date2));
        Map<String,Object> map = new HashMap<>();
        map.put("list",pageInfo.getList());
        map.put("total",pageInfo.getTotal());
        return ResultInfo.createResult("查询成功!",map);

    }


    @ResponseBody
    @DeleteMapping("visitLog")
    @OperationLogger("删除访问日志")
    public ResultInfo delete(@RequestParam Integer id){
        visitLogService.delete(id);
        return ResultInfo.createResult("删除成功!");
    }
}
