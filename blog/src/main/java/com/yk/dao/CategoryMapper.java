package com.yk.dao;


import com.yk.entity.Category;

import java.util.List;

public interface CategoryMapper {

    List<Category> selectCategories();

    Category getCategoryById(long id);

    Category getCategoryByName(String cate);

    Integer addCategory(Category category);

    Integer updateCategory(Category category);

    Integer deleteCategoryById(Long id);

}
