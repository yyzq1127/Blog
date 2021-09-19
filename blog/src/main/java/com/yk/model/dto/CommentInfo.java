package com.yk.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author yk
 * @version 1.0
 * @date 2021/5/12 22:23
 */
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CommentInfo {
    private Long id;
    private String nickname;
    private String email;
    private String content;
    private String avatar;
    private Date createTime;
    private String ip;
    private Boolean published;
    private Boolean adminComment;
    private Integer page;
    private Boolean notice;
    private Long blogId;
    private Long parentCommentId;
    private String parentCommentNickname;
    private String website;
    private String qq;

    private BlogIdAndTitle blog;//所属的文章
    private List<CommentInfo> replyComments = new ArrayList<>();//回复该评论的评论
}
