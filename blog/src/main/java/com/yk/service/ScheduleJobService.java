package com.yk.service;

import com.yk.dao.ScheduleJobMapper;
import com.yk.entity.ScheduleJob;
import com.yk.model.dto.ScheduleJobInfo;
import com.yk.utils.AssertUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author yk
 * @version 1.0
 * @date 2021/5/29 15:22
 */
@Service
public class ScheduleJobService {

    @Resource
    private ScheduleJobMapper scheduleJobMapper;

    public List<ScheduleJob> getJobList() {
        return scheduleJobMapper.getJobList();
    }

    /**
     * 根据jobId更新status
     * @param jobId
     * @param status
     * @return
     */
    @Transactional
    public void updateJobStatus(Long jobId, Byte status) {
        AssertUtil.isTrue(scheduleJobMapper.updateJobStatus(jobId,status)<1,"更新失败!");
    }

    /**
     * 添加操作
     * @param scheduleJob
     * @return
     */
    @Transactional
    public void addJob(ScheduleJob scheduleJob) {
        scheduleJob.setCreateTime(new Date());
        scheduleJob.setStatus((byte)0);
        AssertUtil.isTrue(scheduleJobMapper.addJob(scheduleJob)<1,"添加失败!");
    }

    /**
     * 编辑操作
     * @param scheduleJobInfo
     * @return
     */
    @Transactional
    public void editJob(ScheduleJobInfo scheduleJobInfo) {
        AssertUtil.isTrue(scheduleJobMapper.editJob(scheduleJobInfo)<1,"编辑失败!");
    }

    /**
     * 删除
     * @param jobId
     * @return
     */
    @Transactional
    public void deleteById(Long jobId) {
        AssertUtil.isTrue(scheduleJobMapper.deleteById(jobId)<1,"删除失败!");
    }
}
