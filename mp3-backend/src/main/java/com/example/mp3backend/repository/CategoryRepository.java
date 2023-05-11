package com.example.mp3backend.repository;

import com.example.mp3backend.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Set<Category> findByIdIn(List<Long> ids);
    Optional<Category> findByName(String name);
}
