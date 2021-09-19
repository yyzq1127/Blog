package com.yk.dao;

import com.yk.entity.ExceptionLog;

import java.util.Date;
import java.util.List;

public interface ExceptionLogMapper {
    List<ExceptionLog> getExceptionLogs(Date date1, Date date2);

    Integer delete(Integer id);

    Integer addOperationLog(ExceptionLog exceptionLog);
}
