package com.mertapp.todolist.payload.response;

import lombok.Data;

@Data
public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private Long id;
    private String username;
    private String email;

    public JwtResponse(String jwt, Long id, String username, String email) {
        this.token = jwt;
        this.id = id;
        this.username = username;
        this.email = email;
    }
}
