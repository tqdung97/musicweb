package com.example.mp3backend.reponse;

import com.example.mp3backend.entity.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    private User auth;
    private String token;

    @JsonProperty("isAuthenticated")
    private Boolean isAuthenticated;
}
