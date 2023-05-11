package com.example.mp3backend.repository;

import com.example.mp3backend.entity.Singer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SingerRepository extends JpaRepository<Singer, Long> {
    List<Singer> findAll();
    Optional<Singer> findById(Long id);
    Optional<Singer> findByName(String name);
    List<Singer> findByIdIn(List<Long> ids);
}
