package com.mertapp.todolist.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Generated;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Data
@Entity
@Table(name = "user")
@ApiModel(value = "User Api model documentation", description = "Model")
public class User {
    @ApiModelProperty(value = "Unique id field of user object")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ApiModelProperty(value = "Username field of user object")
    @Column(nullable = false, unique = true, length = 30)
    private String username;

    @ApiModelProperty(value = "emailAddress field of user object")
    @Column(nullable = false, unique = true, length = 40)
    private String email;

    @ApiModelProperty(value = "password field of user object")
    @Column(nullable = false, length = 120)
    private String password;
}
