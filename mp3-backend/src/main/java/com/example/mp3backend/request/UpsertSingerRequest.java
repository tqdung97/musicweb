package com.example.mp3backend.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpsertSingerRequest {
    private String name;
    private String birthday;

    private String country;
    private String image;
    private String description;
}
