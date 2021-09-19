package com.yk.service;

import com.yk.config.RedisKeyConfig;
import com.yk.dao.TagMapper;
import com.yk.model.ResultInfo;
import com.yk.utils.AssertUtil;
import com.yk.entity.Tag;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author yk
 * @version 1.0
 * @date 2021/4/5 15:53
 */
@Service
public class TagService {

    @Resource
    private TagMapper tagMapper;
    @Resource
    private RedisService redisService;

    /**
     * 查询所有标签
     * @return
     */
    public List<Tag> selectTags() {
        return tagMapper.selectTags();
    }

    /**
     * 添加标签
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public ResultInfo addTag(Tag tag) {
        String name = tag.getName();
        String color = tag.getColor();
        AssertUtil.isTrue(name == null || "".equals(name),"标签名称不能为空!");
        AssertUtil.isTrue(color == null || "".equals(color),"标签颜色不能为空!");
        Tag tag1 = new Tag();
        tag1.setName(name);
        tag1.setColor(color);
        AssertUtil.isTrue(tagMapper.addTag(tag1) < 1,"添加失败!");
        //更新缓存
        redisService.deleteCacheByKey(RedisKeyConfig.TAG_CLOUD_LIST);

        return ResultInfo.createResult("添加成功!");

    }

    /**
     * 更新标签
     * @param tag
     * @return ResultInfo
     */
    @Transactional
    public ResultInfo updateTag(Tag tag) {
        Tag temp = tagMapper.getTagById(tag.getId());
        AssertUtil.isTrue(temp == null,"待更新数据不存在!");
        AssertUtil.isTrue(tagMapper.updateTag(tag) < 1,"更新失败!");

        //更新缓存
        redisService.deleteCacheByKey(RedisKeyConfig.TAG_CLOUD_LIST);


        return ResultInfo.createResult("更新成功!");
    }

    /**
     * 删除标签
     * @param id
     * @return ResultInfo
     */
    @Transactional
    public ResultInfo deleteTag(Long id) {
        Tag tag = tagMapper.getTagById(id);
        AssertUtil.isTrue(tag == null,"待删除数据不存在!");
        AssertUtil.isTrue(tagMapper.deleteTag(id) < 1,"删除失败!");
        //更新缓存
        redisService.deleteCacheByKey(RedisKeyConfig.TAG_CLOUD_LIST);
        return ResultInfo.createResult("删除成功!");
    }

    /**
     * 查询所有标签
     * @return List<Tag>
     */
    public List<Tag> selectTagsNoId() {
        String redisKey = RedisKeyConfig.TAG_CLOUD_LIST;
        List list = redisService.getListByKey(redisKey);
        if(list == null||list.size() == 0){
            list = tagMapper.selectTagsNoId();
            redisService.addList(redisKey,list);
        }
        return list;
    }
}
