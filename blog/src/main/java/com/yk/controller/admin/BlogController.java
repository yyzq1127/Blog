package com.yk.controller.admin;

import com.yk.annotation.OperationLogger;
import com.yk.entity.Blog;
import com.yk.entity.Category;
import com.yk.entity.Tag;
import com.yk.model.ResultInfo;
import com.yk.model.dto.BlogInfo;
import com.yk.model.dto.Visibility;
import com.yk.service.*;
import com.yk.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.yk.service.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yk
 * @version 1.0
 * @date 2021/4/4 15:45
 */
@Controller
@RequestMapping("admin")
public class BlogController {


    @Autowired
    private BlogService blogService;
    @Autowired
    private BlogTagService blogTagService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private TagService tagService;

    /**
     * 查询博客列表(blog)
     * 分页多条件查询
     * @return
     */
    @GetMapping("blogs")
    @ResponseBody
    public ResultInfo getDataByQuery(@RequestParam(defaultValue = "") String title,
                                     @RequestParam(defaultValue = "") Integer categoryId){
        return blogService.getDataByQuery(title,categoryId);
    }

    /**
     * 删除blog (blog)
     * 并删除评论(comment)和tag(blog_tag)
     * @param id
     * @return
     */
    @DeleteMapping("blog")
    @ResponseBody
    @OperationLogger("删除博客")
    public ResultInfo deleteBlog(@RequestParam long id){
        //先判断一定存在的标签
        blogTagService.deleteBlogTagByBlogId(id);
        blogService.deleteBlogById(id);
        commentService.deleteCommentByBlogId(id);

        return ResultInfo.createResult("删除成功!");
    }

    /**
     * 查询blog (blog)
     * @param id
     * @return
     */
    @GetMapping("blog")
    @ResponseBody
    public ResultInfo queryBlogById(@RequestParam long id){

        Blog blog = blogService.queryBlogById(id);

        return ResultInfo.createResult("查询成功!",blog);
    }

    /**
     * 查询category(category)和tag(tag)
     * @return
     */
    @GetMapping("categoryAndTag")
    @ResponseBody
    public ResultInfo queryAllCategoryAndTag(){
        List<Category> categories = categoryService.selectCategories();
        List<Tag> tags = tagService.selectTags();

        Map<String, Object> map = new HashMap<>();
        map.put("categories", categories);
        map.put("tags", tags);
        return ResultInfo.createResult("查询成功!",map);
    }
    /**
     * 更新blog
     * @param blogInfo
     * @return
     */
    @PutMapping("blog")
    @ResponseBody
    @OperationLogger("更新博客")
    public ResultInfo updateBlog(@RequestBody BlogInfo blogInfo){
        return blogService.updateBlog(blogInfo);
    }

    /**
     * 添加blog
     * @return
     */
    @PostMapping("blog")
    @ResponseBody
    @OperationLogger("添加博客")
    public ResultInfo addBlog(@RequestBody BlogInfo blogInfo){
        return blogService.addBlog(blogInfo);
    }

    /**
     * 更新置顶状态
     * @param id
     * @param top
     * @return
     */
    @PutMapping("blog/top")
    @ResponseBody
    @OperationLogger("更新博客置顶状态")
    public ResultInfo updateTop(Long id,boolean top){
        return blogService.updateTop(id,top);
    }
    /**
     * 更新推荐状态
     * @param id
     * @param recommend
     * @return
     */
    @PutMapping("blog/recommend")
    @ResponseBody
    @OperationLogger("更新博客推荐状态")
    public ResultInfo updateRecommend(Long id,boolean recommend){
        return blogService.updateRecommend(id,recommend);
    }

    /**
     * 更新可见状态
     * @param visibility
     * @param id
     * @return
     */
    @PutMapping("blog/{id}/visibility")
    @ResponseBody
    @OperationLogger("更新博客可见状态")
    public ResultInfo updateVisibility(@RequestBody Visibility visibility,@PathVariable("id") Long id){
        return blogService.updateVisibility(visibility,id);
    }
}



















