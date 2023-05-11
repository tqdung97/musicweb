package com.example.mp3backend.controller;

import com.example.mp3backend.request.UpsertCategoryRequest;
import com.example.mp3backend.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/admin/categories")
public class CategoryControler {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("")
    public ResponseEntity<?> getCategories() {
        return ResponseEntity.ok(categoryService.getCategories());
    }
    @PostMapping("create")
    public ResponseEntity<?> createCategory(@RequestBody UpsertCategoryRequest request){
       return ResponseEntity.ok(categoryService.createCategory(request));
    }
}
