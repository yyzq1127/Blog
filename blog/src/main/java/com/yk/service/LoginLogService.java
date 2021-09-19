package com.yk.service;

import com.yk.dao.LoginLogMapper;
import com.yk.entity.LoginLog;
import com.yk.utils.AssertUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author yk
 * @version 1.0
 * @date 2021/5/25 21:53
 */
@Service
public class LoginLogService {

    @Resource
    private LoginLogMapper loginLogMapper;

    /**
     * 查询登录日志
     * @return
     * @param date1
     * @param date2
     */
    public List<LoginLog> getLoginLogs(Date date1, Date date2) {
        return loginLogMapper.getLoginLogs(date1,date2);
    }

    @Transactional
    public void delete(Integer id) {
        AssertUtil.isTrue(loginLogMapper.delete(id)<1,"删除失败!");
    }
}
