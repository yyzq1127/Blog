package com.yk.service;

import com.yk.config.RedisKeyConfig;
import com.yk.dao.CategoryMapper;
import com.yk.entity.Category;
import com.yk.exceptions.MyException;
import com.yk.model.ResultInfo;
import com.yk.utils.AssertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author yk
 * @version 1.0
 * @date 2021/4/5 15:51
 */
@Service
public class CategoryService {

    @Resource
    private CategoryMapper categoryMapper;

    @Resource
    private RedisService redisService;
    /**
     * 查询所有分类
     * @return
     */
    public List<Category> selectCategories() {
        String redisKey = RedisKeyConfig.CATEGORY_NAME_LIST;
        List<Category> categoryList = redisService.getListByKey(redisKey);
        if(categoryList == null){
            categoryList = categoryMapper.selectCategories();
            redisService.addList(redisKey,categoryList);
        }
        return categoryList;
    }

    /**
     * 添加分类
     * @param category
     * @return
     */
    @Transactional
    public ResultInfo addCategory(Category category) {
        String name = category.getName();
        AssertUtil.isTrue(name == null || name.equals(""),"分类名称不能为空!");
        Category category1 = new Category();
        category1.setName(name);
        AssertUtil.isTrue(categoryMapper.addCategory(category1) < 1,"分类添加失败!");
        //删除缓存
        redisService.deleteCacheByKey(RedisKeyConfig.CATEGORY_NAME_LIST);
        return ResultInfo.createResult("分类添加成功!");
    }

    /**
     * 更新分类
     * @param category
     * @return
     */
    @Transactional
    public ResultInfo updateCategory(Category category) throws Exception {
        Category temp = categoryMapper.getCategoryById(category.getId());
        if(temp == null){
            //更新缓存
            redisService.deleteCacheByKey(RedisKeyConfig.CATEGORY_NAME_LIST);
            throw new Exception("待更新数据不存在,请刷新页面!");
        }
        AssertUtil.isTrue(categoryMapper.updateCategory(category) < 1,"更新失败!");
        redisService.deleteCacheByKey(RedisKeyConfig.CATEGORY_NAME_LIST);
        //更新首页缓存
        redisService.deleteCacheByKey(RedisKeyConfig.HOME_BLOG_INFO_LIST);
        return ResultInfo.createResult("更新成功!");

    }


    /**
     * 删除分类
     * @param id
     * @return
     */
    @Transactional
    public ResultInfo deleteCategoryById(Long id) throws Exception {
        Category category = categoryMapper.getCategoryById(id);
//        AssertUtil.isTrue(category == null,"待删除数据不存在!");
        if(category == null){
            //更新缓存
            redisService.deleteCacheByKey(RedisKeyConfig.CATEGORY_NAME_LIST);
            throw new Exception("待删除数据不存在,请刷新页面!");
        }
        AssertUtil.isTrue(categoryMapper.deleteCategoryById(id) < 1,"删除失败!");
        //删除缓存
        redisService.deleteCacheByKey(RedisKeyConfig.CATEGORY_NAME_LIST);
        return ResultInfo.createResult("删除成功!");
    }
}
