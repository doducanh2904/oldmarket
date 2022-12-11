package com.due.oldmarket.service.impl;

import com.due.oldmarket.model.Category;
import com.due.oldmarket.reponsitory.CategoryReponsitory;
import com.due.oldmarket.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategorySeviceImpl implements CategoryService {
    @Autowired
    CategoryReponsitory categoryReponsitory;

    public void save(Category category) {
        categoryReponsitory.save(category);
    }

    public List<Category> findAll() {

        return categoryReponsitory.findAll();
    }

    public Category findById(Long idCategory) {

        return categoryReponsitory.findById(idCategory).orElse(null);
    }

    public void update(Long id, Category category) {
        Category category1 = categoryReponsitory.findById(id).get();
        category1.setCategoryName(category.getCategoryName());
        category1.setTotal(category.getTotal());
        categoryReponsitory.save(category1);
    }
}
