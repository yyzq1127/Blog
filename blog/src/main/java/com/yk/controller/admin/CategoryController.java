package com.yk.controller.admin;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yk.annotation.OperationLogger;
import com.yk.entity.Category;
import com.yk.model.Page;
import com.yk.model.ResultInfo;
import com.yk.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yk
 * @version 1.0
 * @date 2021/4/14 20:13
 */
@Controller
@RequestMapping("admin")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;


    /**
     * 分页查询所有分类
     * @return
     */
    @GetMapping("categories")
    @ResponseBody
    public ResultInfo getCategories(){
        List<Category> categories = categoryService.selectCategories();
        PageHelper.startPage(Page.getPageNumber(),Page.getPageSize());
        PageInfo<Category> pageInfo = new PageInfo<>(categories);

        Map<String,Object> map = new HashMap<>();
        map.put("list",pageInfo.getList());
        map.put("total",pageInfo.getTotal());
        return ResultInfo.createResult("查询成功!",map);
    }

    /**
     * 添加分类
     * @return
     * @Param category
     */
    @PostMapping("category")
    @ResponseBody
    @OperationLogger("添加分类")
    public ResultInfo addCategory(@RequestBody Category category){
        return categoryService.addCategory(category);
    }

    /**
     * 更新分类
     * @param category
     * @return
     */
    @PutMapping("category")
    @ResponseBody
    @OperationLogger("更新分类")
    public ResultInfo updateCategory(@RequestBody Category category) throws Exception {
        return categoryService.updateCategory(category);
    }

    /**
     * 删除分类
     * @param id
     * @return
     */
    @DeleteMapping("category")
    @ResponseBody
    @OperationLogger("删除分类")
    public ResultInfo deleteCategoryById(@RequestParam Long id) throws Exception {
        return categoryService.deleteCategoryById(id);
    }
}
