package com.yk.model.dto;

import lombok.Data;

/**
 * @author yk
 * @version 1.0
 * @date 2021/5/24 23:14
 */
@Data
public class TagBlogCount {
    private Long id;
    private String name;//标签名
    private Integer value;//标签下博客数量

}
