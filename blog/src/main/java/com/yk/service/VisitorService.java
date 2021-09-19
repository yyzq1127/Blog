package com.yk.service;

import com.yk.dao.VisitorMapper;
import com.yk.utils.AssertUtil;
import com.yk.entity.Visitor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author yk
 * @version 1.0
 * @date 2021/5/26 22:39
 */
@Service
public class VisitorService {
    @Resource
    private VisitorMapper visitorMapper;

    public List<Visitor> getVisitor(Date date1, Date date2) {
        return visitorMapper.getVisitor(date1,date2);
    }

    @Transactional
    public void delete(String uuid,Integer id) {
        AssertUtil.isTrue(visitorMapper.delete(uuid,id)<1,"删除失败!");
    }
}
