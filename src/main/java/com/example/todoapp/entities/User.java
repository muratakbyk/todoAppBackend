package com.example.todoapp.entities;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.List;

@Entity
@Data
public class User {

    @Id
    @GeneratedValue
    @Column(name="id")
    private Long id;

    @Column(name="name",nullable = false)
    private String name;

    @Column(name="surname")
    private String surname;

    @Column(name="username",nullable = false,unique = true)
    private String userName;

    @Column(name="email",nullable = false,unique = true)
    @Email
    private String email;

    @Column(name="password",nullable = false)
    private String password;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "user")
    private List<Task> tasks;


}
