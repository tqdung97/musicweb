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

    @Query(value = "select  * from  song left join user u on u.id = song.id order by creation_time desc limit 2", nativeQuery = true)
    List<Song> findSongByCreationTime();
}
