package com.example.mp3backend.repository;

import com.example.mp3backend.entity.TokenConfirm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface TokenConfirmRepository extends JpaRepository<TokenConfirm, Long> {
    Optional<TokenConfirm> findByToken(String token);
}