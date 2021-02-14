package com.mertapp.todolist.payload.response;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class NoteResponse {
    private Long id;
    private Long userid;
    private String text;
    private String status;
    private Date insert_date;
    private Date update_date;

    public NoteResponse(Long id, Long userid, String text, String status, Date insert_date, Date update_date) {
        this.id = id;
        this.userid = userid;
        this.text = text;
        this.status = status;
        this.insert_date = insert_date;
        this.update_date = update_date;
    }
}
