package com.example.mp3backend.controller;

import com.example.mp3backend.request.UpsertSongRequest;
import com.example.mp3backend.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api")
public class SongController {
    @Autowired
    SongService songService;
    // Get All Song
    @GetMapping("songs")
    public ResponseEntity<?> getAllSong(){
       return ResponseEntity.ok(songService.getAllSong());
    }
    //Get Song By Id
    @GetMapping("song/{id}")
    public ResponseEntity<?> getSongById(@PathVariable Long id){
        return ResponseEntity.ok(songService.getSongById(id));
    }
    //create song

    @PostMapping("{idUser}/created-song")
    public ResponseEntity<?> createSong(@RequestBody UpsertSongRequest request, @PathVariable Long idUser){
        return ResponseEntity.ok(songService.createSong(request, idUser));
    }

    //get song by user
    @GetMapping("api/{idUser}/song")
    public ResponseEntity<?> getSongByUser(@PathVariable Long idUser){
        return ResponseEntity.ok(songService.getSongByUser(idUser));
    }
    //updateSong
    @PutMapping("api/{idUser}/updateSong/{id}")
    public ResponseEntity<?> updateSong(@RequestBody UpsertSongRequest request, @PathVariable Long id, @PathVariable Long idUser){
        return ResponseEntity.ok(songService.updateSong(request, id, idUser));
    }

    @GetMapping("songs/latest-song")
    public ResponseEntity<?> showLatestSong(){
        return ResponseEntity.ok(songService.showLatestSong());
    }
}
