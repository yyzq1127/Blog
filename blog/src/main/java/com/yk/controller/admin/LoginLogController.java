package com.yk.controller.admin;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yk.entity.LoginLog;
import com.yk.model.ResultInfo;
import com.yk.service.LoginLogService;
import com.yk.annotation.OperationLogger;
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
 * @date 2021/5/25 21:54
 */
@Controller
@RequestMapping("admin")
public class LoginLogController {
    @Resource
    private LoginLogService loginLogService;

    /**
     * 查询登录日志
     * @param pageNum
     * @param pageSize
     * @return
     */
    @ResponseBody
    @GetMapping("loginLogs")
    public ResultInfo loginLogs(@RequestParam(defaultValue = "") String date,
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
        PageInfo<LoginLog> pageInfo = new PageInfo<>(loginLogService.getLoginLogs(date1,date2));
        Map<String,Object> map = new HashMap<>();
        map.put("list",pageInfo.getList());
        map.put("total",pageInfo.getTotal());
        return ResultInfo.createResult("查询成功!",map);
    }

    @ResponseBody
    @DeleteMapping("loginLog")
    @OperationLogger("删除登录日志")
    public ResultInfo delete(@RequestParam Integer id){
        loginLogService.delete(id);
        return ResultInfo.createResult("删除成功!");
    }
}
