package com.mertapp.todolist.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "note")
@ApiModel(value = "Note Api model documentation", description = "Model")
public class Note {
    @ApiModelProperty(value = "Unique id field of note object")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ApiModelProperty(value = "Unique id field of user object for userid")
    @Column(nullable = false, length = 40)
    private Long userid;

    @ApiModelProperty(value = "Text field of note object")
    @Column(nullable = false, length = 40)
    private String text;

    @ApiModelProperty(value = "Status field of note object - Contains checked status")
    @Column(nullable = false, unique = true, length = 40)
    private String status;

    private Date insert_date;
    private Date update_date;
}
