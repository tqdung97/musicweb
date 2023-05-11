package com.example.mp3backend.controller;

import com.example.mp3backend.entity.User;
import com.example.mp3backend.exception.NotFoundException;
import com.example.mp3backend.repository.UserRepository;
import com.example.mp3backend.request.UpsertUserRequest;
import com.example.mp3backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api")
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;
    // Get all User
    @GetMapping("/admin/users")
    public ResponseEntity<?> getAllUser(){
        return ResponseEntity.ok(userService.getAllUser());
    }

    //Get User By Id
    @GetMapping("/user/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id){
        return ResponseEntity.ok(userService.getUserById(id));
    }

    //Get Id By Email
    @GetMapping("/{email}")
    public ResponseEntity<?> getIdByUser(@PathVariable String email){
        User u = userRepository.findIdByEmail(email).orElseThrow(() ->{
            throw new NotFoundException("Không tìm thấy email");
        });
        return ResponseEntity.ok(u);
    }

    // Update User
    @PutMapping("/user/{id}/update")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody UpsertUserRequest request){
        return ResponseEntity.ok(userService.updateUser(id, request));
    }


}
