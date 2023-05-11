package com.example.mp3backend.repository;

import com.example.mp3backend.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {
    @Query("select i from Image i where i.user.id = ?1 order by i.createdAt DESC")
    List<Image> findByUser_IdOrderByCreatedAtDesc(Long id);
}
