package com.yk.service;

import com.yk.utils.AssertUtil;
import com.yk.dao.ScheduleJobLogMapper;
import com.yk.entity.ScheduleJobLog;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author yk
 * @version 1.0
 * @date 2021/5/26 21:01
 */
@Service
public class JobLogService {
    @Resource
    private ScheduleJobLogMapper scheduleJobLogMapper;

    public List<ScheduleJobLog> getJobLogs(Date date1, Date date2) {
        return scheduleJobLogMapper.getJobLogs(date1,date2);
    }

    @Transactional
    public void delete(Integer id) {
        AssertUtil.isTrue(scheduleJobLogMapper.delete(id)<1,"删除失败!");
    }
}
