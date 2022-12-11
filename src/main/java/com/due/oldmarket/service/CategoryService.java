package com.due.oldmarket.service;

import com.due.oldmarket.model.Category;

import java.util.List;

public interface CategoryService {
    void save (Category category);
    List<Category> findAll ();
    Category findById(Long idCategory);
    void update(Long id, Category category);

}
