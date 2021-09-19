package com.yk.model.dto;

import lombok.Data;

import java.util.Date;

/**
 * @author yk
 * @version 1.0
 * @date 2021/5/29 15:54
 */
@Data
public class ScheduleJobInfo {
    private Long jobId;

    private String beanName;

    private String methodName;

    private String params;

    private String cron;

    private Boolean status;

    private String remark;

    private Date createTime;
}
