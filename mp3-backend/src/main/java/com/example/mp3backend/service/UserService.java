package com.example.mp3backend.service;

import com.example.mp3backend.entity.User;
import com.example.mp3backend.exception.NotFoundException;
import com.example.mp3backend.repository.UserRepository;
import com.example.mp3backend.request.UpsertUserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;


    public List<User> getAllUser() {
        return userRepository.findAllByRoles("USER");
    }

    public User getUserById(Long id) {
        return userRepository.findUserById(id).orElseThrow(() -> {
            throw new NotFoundException("Not found user with id " + id);
        });
    }

    public User updateUser(Long id, UpsertUserRequest request) {
        User user = userRepository.findUserById(id).orElseThrow(() -> {
            throw new NotFoundException("Not found user with id " + id);
        });
        user.setName(request.getName());
        user.setPassword(request.getPassword());
        user.setAvatar(request.getAvatar());
        return userRepository.save(user);
    }

}
