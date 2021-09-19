package com.yk.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Friend {
    private Long id;
    private String nickname;
    private String description;
    private String website;
    private String avatar;
    private Boolean published;
    private Integer views;
    private Date createTime;

}
