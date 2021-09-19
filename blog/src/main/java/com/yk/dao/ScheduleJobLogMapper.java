package com.yk.dao;

import com.yk.entity.ScheduleJobLog;

import java.util.Date;
import java.util.List;

public interface ScheduleJobLogMapper {

    List<ScheduleJobLog> getJobLogs(Date date1, Date date2);

    int delete(Integer id);

}
