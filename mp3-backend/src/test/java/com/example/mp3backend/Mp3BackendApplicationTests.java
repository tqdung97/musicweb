package com.example.mp3backend;

import com.example.mp3backend.entity.User;
import com.example.mp3backend.repository.UserRepository;
import com.example.mp3backend.security.JwtTokenUtil;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
class Mp3BackendApplicationTests {
    @Autowired
    JwtTokenUtil tokenUtil;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Test
    void generate_token_test(){
        User user = userRepository.findByEmail("tqdung2301@gmail.com").orElse(null);

        String token = tokenUtil.generateToken(user);
        System.out.println("Đây:" + token);
    }

    @Test
    void parse_token_test(){
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJhdXRob3JpdGllcyI6W3siYXV0aG9yaXR5IjoiUk9MRV9bXSJ9XSwic3ViIjoidHFkdW5nMjMwMUBnbWFpbC5jb20iLCJpYXQiOjE2ODA2MDc2MTgsImV4cCI6MTY4MDY5NDAxOH0.sw3Xe3wR2ycyTEt9c9OUf0DuMhKpbmtTY6ugGijOc6g";
        Claims claims = tokenUtil.getClaimsFromToken(token);
        System.out.println(claims.getSubject());
    }
}