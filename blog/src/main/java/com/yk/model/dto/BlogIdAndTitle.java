package com.yk.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author yk
 * @version 1.0
 * @date 2021/5/14 17:02
 */
@NoArgsConstructor
@Getter
@Setter
@ToString
public class BlogIdAndTitle {
    private Long id;
    private String title;
}
