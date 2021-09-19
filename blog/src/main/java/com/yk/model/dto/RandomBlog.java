package com.yk.model.dto;

import lombok.Data;

import java.util.Date;

/**
 * @author yk
 * @version 1.0
 * @date 2021/6/11 17:16
 */
@Data
public class RandomBlog {
    private Long id;
    private String title;//文章标题
    private String firstPicture;//文章首图，用于随机文章展示
    private Date createTime;//创建时间
    private String password;//文章密码
    private Boolean privacy;//是否私密文章
}
