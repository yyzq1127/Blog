package com.yk.service;

import com.yk.utils.AssertUtil;
import com.yk.dao.VisitLogMapper;
import com.yk.entity.VisitLog;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author yk
 * @version 1.0
 * @date 2021/5/26 21:51
 */
@Service
public class VisitLogService {
    @Resource
    private VisitLogMapper visitLogMapper;

    public List<VisitLog> getVisitLogs(String uuid,Date date1, Date date2) {
        return visitLogMapper.getVisitLogs(uuid,date1,date2);
    }

    @Transactional
    public void delete(Integer id) {
        AssertUtil.isTrue(visitLogMapper.delete(id)<1,"删除失败!");
    }
}
