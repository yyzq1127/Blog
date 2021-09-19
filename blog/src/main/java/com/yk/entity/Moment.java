package com.yk.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Moment {
    private Long id;

    private Date createTime;

    private Integer likes;

    private Boolean published;

    private String content;


}
