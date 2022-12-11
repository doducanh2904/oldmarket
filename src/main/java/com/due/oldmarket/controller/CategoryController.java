package com.due.oldmarket.controller;

import com.due.oldmarket.model.Category;
import com.due.oldmarket.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin
@RestController
@RequestMapping(value = "/category")
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @GetMapping(value = "/search/{id}")
    public ResponseEntity<Category> findById(@PathVariable(value = "id") Long id) {
        Category category = categoryService.findById(id);
        return new ResponseEntity<>(category, HttpStatus.OK);
    }


    @GetMapping(value = "/searchAll")
    public ResponseEntity<List<Category>> findAll() {
        List<Category> categoryList = categoryService.findAll();
        if (categoryList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(categoryList, HttpStatus.OK);
    }

    @PostMapping(value = "/insert")
    public ResponseEntity<Category> insert(@RequestBody Category category, BindingResult result) {
        if (result.hasErrors()) {
            StringBuilder errors = new StringBuilder();
            for (final FieldError error : result.getFieldErrors()) {
                System.out.println(errors.append("/" + error.getDefaultMessage()));
            }
        }
        categoryService.save(category);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(value = "/update/{id}", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Category> update(@RequestBody Category category, @PathVariable(value = "'id") Long id) {
        categoryService.update(id, category);
        return new ResponseEntity<>(categoryService.findById(id), HttpStatus.OK);
    }
}
