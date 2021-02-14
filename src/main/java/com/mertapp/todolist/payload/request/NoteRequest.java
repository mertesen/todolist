package com.mertapp.todolist.payload.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NoteRequest {
    private Long id;
    private Long userId;
    private String text;
    private String status;
}
