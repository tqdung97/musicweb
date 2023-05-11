package com.example.mp3backend.request;

import com.example.mp3backend.entity.Singer;
import com.example.mp3backend.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class UpsertSongRequest {
    private String name;
    private String file;
    private Long numberOfView;
    private String author;
    private String image;
    private String lyric;
    private List<Long> singers;

    private List<Long> categoryIds;
    private User user;
}
