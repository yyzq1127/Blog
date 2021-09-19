package com.yk.dao;

import com.yk.entity.LoginLog;

import java.util.Date;
import java.util.List;

public interface LoginLogMapper {

    List<LoginLog> getLoginLogs(Date date1, Date date2);

    Integer delete(Integer id);

}
