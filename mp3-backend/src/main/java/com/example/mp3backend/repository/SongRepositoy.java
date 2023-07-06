package com.example.mp3backend.repository;

import com.example.mp3backend.entity.Song;
import com.example.mp3backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SongRepositoy extends JpaRepository<Song, Long> {
    List<Song> findAll();

    Optional<Song> findById(Long id);

    Iterable<Song> findSongByUser(User user);

    @Query(value = "select * from song order by creation_time desc limit 10", nativeQuery = true)
    List<Song> findAllByCreationTimeOrderByCreationTime();

    @Query(value = "select * from song order by num_of_view desc limit 10", nativeQuery = true)
    Iterable<Song> findAllByNumberOfViewOrderByNumberOfView();

    Iterable<Song> findAllByNameContains(String keyword);
    Iterable<Song> findAllByUser_Id(Long idUser);


}
