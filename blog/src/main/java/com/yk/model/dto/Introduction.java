package com.yk.model.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author yk
 * @version 1.0
 * @date 2021/6/11 22:30
 */
@Data
public class Introduction {
    private String avatar;
    private String github;
    private String qq;
    private String name;
    private String bilibili;
    private String netease;
    private String email;
    private List<Map<String, Object>> favorites;
    private List<String> rollText;
}
