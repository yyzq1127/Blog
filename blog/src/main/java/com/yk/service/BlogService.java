package com.yk.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yk.config.RedisKeyConfig;
import com.yk.dao.BlogMapper;
import com.yk.dao.CategoryMapper;
import com.yk.dao.TagMapper;
import com.yk.entity.Blog;
import com.yk.entity.Category;
import com.yk.entity.Tag;
import com.yk.model.Page;
import com.yk.model.ResultInfo;
import com.yk.model.dto.*;
import com.yk.utils.AssertUtil;
import com.yk.dao.BlogTagMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author yk
 * @version 1.0
 * @date 2021/4/4 15:51
 */
@Service
public class BlogService {

    @Resource
    private BlogMapper blogMapper;

    @Resource
    private CategoryMapper categoryMapper;

    @Resource
    private BlogTagMapper blogTagMapper;

    @Resource
    private TagMapper tagMapper;

    @Resource
    private RedisService redisService;
    /**
     * 查询博客列表
     * @return
     */
    public ResultInfo getDataByQuery(String title, Integer categoryId) {

        Map<String,Object> map = new HashMap<>();
        //每页十条
        PageHelper.startPage(Page.getPageNumber(),Page.getPageSize());
        PageInfo<Blog> pageInfo = new PageInfo<>(blogMapper.getDataByQuery(title,categoryId));

        List<Category> categories = categoryMapper.selectCategories();
        map.put("categories",categories);
        map.put("blogs", pageInfo);
        map.put("totalPage",pageInfo.getTotal());
        map.put("list",pageInfo.getList());
        return ResultInfo.createResult("查询成功!",map);
    }

    /**
     * 删除blog
     * @param id
     */
    @Transactional
    public void deleteBlogById(long id) {
        AssertUtil.isTrue(blogMapper.selectBlogById(id) == null,"待删除博客不存在!");

        AssertUtil.isTrue(blogMapper.deleteBlogById(id) != 1,"删除失败!");

    }

    /**
     * 通过id查询blog
     * @param id
     */
    public Blog queryBlogById(long id) {
        Blog blog = blogMapper.selectBlogById(id);
        AssertUtil.isTrue(blog == null,"该博客不存在!");
        return blog;
    }

    /**
     * 博客更新(blog)
     * @param blogInfo
     * @return
     */
    @Transactional
    public ResultInfo updateBlog(BlogInfo blogInfo) {
        return updateBlogMethod(blogInfo,"更新");
    }

    /**
     * 博客添加(blog)
     * @param blogInfo
     * @return
     */
//    @Transactional
    public ResultInfo addBlog(BlogInfo blogInfo) {
        return updateBlogMethod(blogInfo,"添加");
    }
    /**
     * 博客更新 or 添加方法
     * @param blogInfo
     * @param type
     * @return ResultInfo
     */
    private ResultInfo updateBlogMethod(BlogInfo blogInfo,String type) {

        //非空判断
        AssertUtil.isTrue(StringUtils.isBlank(blogInfo.getTitle()),"标题不能为空!");
        AssertUtil.isTrue(StringUtils.isBlank(blogInfo.getFirstPicture()),"首图不能为空!");
        AssertUtil.isTrue(StringUtils.isBlank(blogInfo.getContent()),"正文不能为空!");
        AssertUtil.isTrue(StringUtils.isBlank(blogInfo.getDescription()),"描述不能为空!");
        AssertUtil.isTrue(blogInfo.getCate() == null,"分类不能为空!");
        AssertUtil.isTrue(blogInfo.getWords() == null || blogInfo.getWords() < 0,"参数非法!");

        //分类
        Object cate = blogInfo.getCate();
        if (cate instanceof Integer) {
            //已存在分类
            Category category = categoryMapper.getCategoryById(((Integer)cate).longValue());
            blogInfo.setCategory(category);
        } else if(cate instanceof String) {
            //添加新分类
            Category category = categoryMapper.getCategoryByName((String) cate);
            AssertUtil.isTrue(category != null,"分类已存在!");


            Category category1 = new Category();
            category1.setName((String) cate);
            AssertUtil.isTrue(categoryMapper.addCategory(category1) < 1,"新分类添加失败!");//操作category表
            blogInfo.setCategory(category1);
        }

        //标签
        List<Object> tagList = blogInfo.getTagList();
        List<Tag> tags = new ArrayList<>();
        //blogTagMapper.deleteBlogTagByBlogId(blogInfo.getId());
        for(Object tag : tagList){
            if(tag instanceof Integer){
                //已存在的标签
                tags.add(tagMapper.getTagById(((Integer)tag).longValue()));
            } else if (tag instanceof String) {
                //添加新标签
                Tag tag1 = new Tag();
                tag1.setName((String) tag);
                AssertUtil.isTrue(tagMapper.addTag(tag1)<1,"新标签添加失败!");//操作tag表
                tags.add(tag1);
            }
        }
        //默认值
        if(blogInfo.getReadTime() == null || blogInfo.getReadTime() < 0){
            blogInfo.setReadTime(blogInfo.getWords() /200);
        }
        if(blogInfo.getViews() == null || blogInfo.getViews() < 0){
            blogInfo.setViews(0);
        }
        blogInfo.setUpdateTime(new Date());

        //操作执行
        if("更新".equals(type)){
            //操作blog_tag表
            blogTagMapper.deleteBlogTagByBlogId(blogInfo.getId());//删除原有数据
            for(Tag tag : tags){
                //删除后添加
                blogTagMapper.addBlogTag(blogInfo.getId(),tag.getId());
            }
            AssertUtil.isTrue(blogMapper.updateBlog(blogInfo) < 1,"更新失败!");
            return ResultInfo.createResult("更新成功!");
        } else {
            blogInfo.setCreateTime(new Date());
            //操作blog_tag表
            AssertUtil.isTrue(blogMapper.addBlog(blogInfo) < 1,"添加失败!");//先添加获取blogInfo.getId()
            //更新blog缓存
            redisService.deleteCacheByKey(RedisKeyConfig.NEW_BLOG_LIST);
            redisService.deleteCacheByKey(RedisKeyConfig.HOME_BLOG_INFO_LIST);
            for(Tag tag : tags){
                //直接添加
                blogTagMapper.addBlogTag(blogInfo.getId(),tag.getId());
                //更新Tag缓存
                redisService.deleteCacheByKey(RedisKeyConfig.TAG_CLOUD_LIST);
            }

            return ResultInfo.createResult("添加成功!");
        }
    }

    /**
     * 更新置顶状态
     * @param id
     * @param top
     * @return
     */
    @Transactional
    public ResultInfo updateTop(Long id, boolean top) {
        AssertUtil.isTrue(blogMapper.updateTop(id,top)<1,"置顶状态更新失败!");
        return ResultInfo.createResult("置顶状态更新成功!");
    }

    /**
     * 更新推荐状态
     * @param id
     * @param recommend
     * @return
     */
    @Transactional
    public ResultInfo updateRecommend(Long id, boolean recommend) {
        AssertUtil.isTrue(blogMapper.updateRecommend(id,recommend)<1,"推荐状态更新失败!");
        return ResultInfo.createResult("推荐状态更新成功!");
    }

    /**
     * 更新Visibility
     * @param visibility
     * @param id
     * @return
     */
    @Transactional
    public ResultInfo updateVisibility(Visibility visibility, Long id) {
        AssertUtil.isTrue(blogMapper.updateVisibility(visibility,id)<1,"visibility更新失败!");
        return ResultInfo.createResult("博客可见性更新成功!");
    }

    //查询BlogId和Title
    public List<BlogList> queryBlogIdAndTitle() {
        return blogMapper.queryBlogIdAndTitle();
    }

    //查询所有blog
    public List<BlogInfo> getBlogByIsPublished() {
        return blogMapper.getBlogByIsPublished();
    }
    //随机博客显示3条
    private static final int randomBlogPageSize = 3;
    //查询随机blog
    public List<RandomBlog> getRandomBlog() {
        return blogMapper.getRandomBlog(randomBlogPageSize);
    }

    //最新推荐博客显示3条
    private static final int newBlogPageSize = 3;


    public List<NewBlog> getNewBlog() {
        String redisKey = RedisKeyConfig.NEW_BLOG_LIST;
        List list = redisService.getListByKey(redisKey);
        if(list == null||list.size() == 0){
            list = blogMapper.getNewBlog(newBlogPageSize);
            redisService.addList(redisKey,list);
        }
        return list;
    }

    //通过id查询blog
    public Blog getBlogById(Long id) {
        return blogMapper.getBlogById(id);
    }

    //查询评论开关
    public Boolean getIsCommentEnabled(Long blogId) {
        return blogMapper.getIsCommentEnabledById(blogId);
    }

    //通过分类名查询博客
    public List<Blog> getBlogByCategoryName(String categoryName) {
        return blogMapper.getBlogByCategoryName(categoryName);
    }

    //返回map<日期,对应blog列表>
    public Map<String, Object> getArchiveBlogIsPublish() {
        Map<String,Object> map = new HashMap<>();
        List<String> stringList = blogMapper.getDay();
        for (String s : stringList) {
            List<ArchiveBlog> archiveBlogList = blogMapper.getBLogByDayIsPublish(s);
            for (ArchiveBlog archiveBlog : archiveBlogList) {
                if("".equals(archiveBlog.getPassword())){
                    archiveBlog.setPrivacy(false);
                } else {
                    archiveBlog.setPrivacy(true);
                    archiveBlog.setPassword(null);
                }
            }
            map.put(s,archiveBlogList);
        }
        Map<String,Object> map1 = new HashMap<>();
        map1.put("blogMap",map);
        return map1;
    }

    //查询公开博客数
    public Integer getCountIsPublish() {
        return blogMapper.getCountIsPublish();
    }

    //查询password
    public String getPasswordById(Integer blogId) {
        return blogMapper.getPasswordById(blogId);
    }
}
