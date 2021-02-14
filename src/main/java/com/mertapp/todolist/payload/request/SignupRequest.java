package com.mertapp.todolist.payload.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequest {
    private String userName;
    private String email;
    private String password;
}
