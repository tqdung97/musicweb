package com.example.mp3backend.service;

import com.example.mp3backend.entity.*;
import com.example.mp3backend.exception.BadRequestException;
import com.example.mp3backend.exception.NotFoundException;
import com.example.mp3backend.repository.*;
import com.example.mp3backend.request.UpsertSongRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Service
public class SongService {

    @Autowired
    private SongRepositoy songRepositoy;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SingerRepository singerRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    public List<Song> getAllSong() {
        return songRepositoy.findAll();
    }

    public Song getSongById(Long id) {
        return songRepositoy.findById(id).orElseThrow(() -> {
            throw new NotFoundException("Không tìm thấy bài hát với id = " + id);
        });
    }

    public Iterable<Song> getSongByUser(Long idUser) {
        User user = userRepository.findById(idUser).orElseThrow(() -> {
            throw new BadRequestException("Bạn không có quyền");
        });
        return songRepositoy.findSongByUser(user);
    }

    public Song createSong(UpsertSongRequest request, Long idUser) {
        userRepository.findById(idUser).orElseThrow(() -> {
            throw new BadRequestException("Hãy đăng nhập hoặc đăng ký để thực hiện");
        });
        Set<Category> categories = categoryRepository.findByIdIn(request.getCategoryIds());
        List<Singer> singers = singerRepository.findByIdIn(request.getSingers());
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findNameByEmail(email).orElseThrow(() -> {
            throw new NotFoundException("Không tìm thấy User với email = " + email);
        });

        Song song = Song.builder()
                .name(request.getName())
                .numberOfView(0L)
                .lyric(request.getLyric())
                .file(request.getFile())
                .author(request.getAuthor())
                .creationTime(LocalDateTime.now())
                .category(categories)
                .user(user)
                .singers(singers)
                .build();
        return songRepositoy.save(song);
    }

    public Song updateSong(UpsertSongRequest request, Long id, Long idUser) {
        Song song = songRepositoy.findById(id).orElseThrow(() -> {
            throw new NotFoundException("Không tìm thấy bài hát với id " +id );
        });
        Set<Category> categories = categoryRepository.findByIdIn(request.getCategoryIds());
        User user = userRepository.findById(idUser).orElseThrow(() -> {
            throw new BadRequestException("Bạn không có quyền ");
        });
        song.setLyric(request.getLyric());
        song.setImage(request.getImage());
        song.setAuthor(request.getAuthor());
        song.setCategory(categories);
        song.setUser(user);
        return songRepositoy.save(song);
    }

    public List<Song> showLatestSong() {
        return songRepositoy.findAllByCreationTimeOrderByCreationTime();
    }
}
