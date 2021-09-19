package com.yk.dao;

import com.yk.model.dto.TagBlogCount;
import com.yk.entity.Tag;

import java.util.List;

public interface TagMapper {

    List<Tag> selectTags();

    Tag getTagById(long id);

    Integer addTag(Tag tag);

    Integer updateTag(Tag tag);

    Integer deleteTag(Long id);

    List<TagBlogCount> getTagBlogCount();

    List<Tag> getTagList();

    List<Tag> selectTagsNoId();
}
