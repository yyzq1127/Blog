package com.yk.dao;

import com.yk.entity.Moment;

import java.util.List;

public interface MomentMapper {

    List<Moment> selectMoments();

    Moment selectMomentById(Long id);

    Integer updatePublished(Long id, boolean published);

    Integer deleteMomentById(Long id);

    Integer addMoment(Moment moment);

    Integer updateMoment(Moment moment);

    void like(Long id);
}
