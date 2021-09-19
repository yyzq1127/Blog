package com.yk.model.dto;

import lombok.*;

/**
 * @author yk
 * @version 1.0
 * @date 2021/4/10 20:50
 */
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Visibility {

    Boolean appreciation;
    Boolean commentEnabled;
    String password;
    Boolean published;
    Boolean recommend;
    Boolean top;

}
