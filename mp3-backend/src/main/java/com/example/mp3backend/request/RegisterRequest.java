package com.example.mp3backend.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RegisterRequest {
    private String name;
    private String email;
    private String password;
}

