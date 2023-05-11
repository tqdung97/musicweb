package com.example.mp3backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Singer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "birtday")
    private String birtday;

    @Column(name = "image")
    private String image;

    @Column(name = "country")
    private String country;
    @Column(name = "description", length = 6000)
    private String description;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "singer_song",
            joinColumns = @JoinColumn(name = "singer_id"),
            inverseJoinColumns = @JoinColumn(name = "song_id"))
    private List<Song> songs;


}


