package com.yk.dao;

import com.yk.model.dto.ScheduleJobInfo;
import com.yk.entity.ScheduleJob;

import java.util.List;

public interface ScheduleJobMapper {

    List<ScheduleJob> getJobList();

    Integer updateJobStatus(Long jobId, Byte status);

    Integer addJob(ScheduleJob s);

    Integer editJob(ScheduleJobInfo scheduleJobInfo);

    Integer deleteById(Long jobId);

}
