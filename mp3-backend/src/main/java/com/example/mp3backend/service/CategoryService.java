package com.example.mp3backend.service;

import com.example.mp3backend.entity.Category;
import com.example.mp3backend.exception.NotFoundException;
import com.example.mp3backend.repository.CategoryRepository;
import com.example.mp3backend.request.UpsertCategoryRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    CategoryRepository categoryRepository;
    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    public Category createCategory(UpsertCategoryRequest request) {
       if(categoryRepository.findByName(request.getName()).isPresent()){
           throw new RuntimeException("Category đã tồn tại");
        }
       Category category = Category.builder()
               .name(request.getName())
               .build();

        return categoryRepository.save(category);
    }
}
