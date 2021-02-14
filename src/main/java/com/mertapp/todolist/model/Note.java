package com.mertapp.todolist.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "note")
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 40)
    private Long userid;

    @Column(nullable = false, length = 40)
    private String text;

    @Column(nullable = false, unique = true, length = 40)
    private String status;

    private Date insert_date;
    private Date update_date;
}
