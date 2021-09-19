package com.yk.model.dto;

import lombok.Data;

/**
 * @author yk
 * @version 1.0
 * @date 2021/6/13 15:57
 */
@Data
public class ArchiveBlog {
    private Long id;
    private String title;
    private String day;
    private String password;
    private Boolean privacy;
}
