package com.example.mp3backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Builder
@Table(name = "song")
public class Song {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "file", nullable = false, unique = true)
    private String file;

    @Column(name = "creationTime", nullable = false)
    private LocalDateTime creationTime;

    @Column(name = "numOfView", nullable = false)
    private Long numberOfView;

    @Column(name = "author")
    private String author;

    @Column(name = "image")
    private String image;

    @Column(name = "lyric", length = 6000)
    private String lyric;

    @ManyToMany
    @JoinColumn(name = "category_id")
    private Set<Category> category = new LinkedHashSet<>();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToMany(mappedBy = "songs")
    @ToString.Exclude
    private List<Singer> singers;

}

