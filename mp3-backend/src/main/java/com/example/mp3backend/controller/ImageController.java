package com.example.mp3backend.controller;

import com.example.mp3backend.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/images")
public class ImageController {

    @Autowired
    private ImageService imageService;

    // Lấy danh sách ảnh của user
    @GetMapping("")
    public ResponseEntity<?> getAllImage() {
        return ResponseEntity.ok(imageService.getAllImage());
    }

    // Xem ảnh
    @GetMapping("{id}")
    public ResponseEntity<?> readImage(@PathVariable Long id) {
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(imageService.readImage(id));
    }

    // Upload ảnh
    @PostMapping("")
    public ResponseEntity<?> uploadImage(@ModelAttribute("file") MultipartFile file) {
        return new ResponseEntity<>(imageService.uploadImage(file), HttpStatus.CREATED);
    }

    // Xóa ảnh
    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteImage(@PathVariable Long id) {
        imageService.deleteImage(id);
        return ResponseEntity.noContent().build(); // 204
    }
}
