package com.yk.dao;

import com.yk.entity.Visitor;

import java.util.Date;
import java.util.List;

public interface VisitorMapper {


    List<Visitor> getVisitor(Date date1, Date date2);

    Integer delete(String uuid,Integer id);

}
