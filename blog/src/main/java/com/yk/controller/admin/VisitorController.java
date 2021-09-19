package com.yk.controller.admin;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yk.service.VisitorService;
import com.yk.annotation.OperationLogger;
import com.yk.entity.Visitor;
import com.yk.model.ResultInfo;
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
 * @date 2021/5/26 22:41
 */
@Controller
@RequestMapping("admin")
public class VisitorController {

    @Resource
    private VisitorService visitorService;

    @GetMapping("visitors")
    @ResponseBody
    public ResultInfo getVisitor(@RequestParam(defaultValue = "") String date,
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
        PageInfo<Visitor> pageInfo = new PageInfo<>(visitorService.getVisitor(date1,date2));
        Map<String,Object> map = new HashMap<>();
        map.put("list",pageInfo.getList());
        map.put("total",pageInfo.getTotal());
        return ResultInfo.createResult("查询成功!",map);

    }


    @ResponseBody
    @DeleteMapping("visitor")
    @OperationLogger("删除访客信息")
    public ResultInfo delete(@RequestParam Integer id,
                             @RequestParam String uuid){
        visitorService.delete(uuid,id);
        return ResultInfo.createResult("删除成功!");
    }

}
