package com.example.mp3backend.controller;

import com.example.mp3backend.entity.Singer;
import com.example.mp3backend.request.UpsertSingerRequest;
import com.example.mp3backend.service.SingerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api")
public class Singercontroller {
    @Autowired
    SingerService singerService;
    //Get all Singer
    @GetMapping("/singers")
    public ResponseEntity<?>getAllSinger(){
        return ResponseEntity.ok(singerService.getAllSinger());
    }
    //Get Singer By Id
    @GetMapping( "/singer/{id}")
    public ResponseEntity<?> getSingerById(@PathVariable Long id){
        return ResponseEntity.ok(singerService.getSingerById(id));
    }

    //Created Singer
    @PostMapping("/admin/singer/created")
    public ResponseEntity<?> createdSinger(@RequestBody UpsertSingerRequest request){
        return ResponseEntity.ok(singerService.createSinger(request));
    }

    //update singer
    @PutMapping("/admin/singer/update/{id}")
    public ResponseEntity<?> updateSinger(@PathVariable Long id,@RequestBody UpsertSingerRequest request){
        return ResponseEntity.ok(singerService.updateSinger(id, request));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteSinger(@PathVariable Long id){
        singerService.deleteSinger(id);
        return ResponseEntity.noContent().build();
    }
}
