package com.yk.dao;


import com.yk.entity.VisitLog;

import java.util.Date;
import java.util.List;

public interface VisitLogMapper {

    int countVisitLogByToday(Date date);

    Integer delete(Integer id);

    List<VisitLog> getVisitLogs(String uuid, Date date1, Date date2);
}
