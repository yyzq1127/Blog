package com.yk.dao;

import com.yk.entity.OperationLog;

import java.util.Date;
import java.util.List;

public interface OperationLogMapper {

    List<OperationLog> getOperationLogs(Date date1, Date date2);

    Integer delete(Integer id);

    Integer addOperationLog(OperationLog operationLog);
}
