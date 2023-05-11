package com.example.mp3backend.repository;

import com.example.mp3backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findAllByRoles(String role);
    Optional<User>findUserById(Long id);
    Optional<User> findByEmail(String email);
    Optional<User> findIdByEmail(String email);
    Optional<User> findNameByEmail(String email);


}
