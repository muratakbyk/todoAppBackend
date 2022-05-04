package com.example.todoapp.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="task")
@Data
public class Task {
    @Id
    @GeneratedValue
    @Column(name="id")
    private Long id;

    @Column(name="taskName")
    private String taskName;

    @Column(name="content")
    private String content;

    @Column(name="isDone")
    private boolean isDone = false;

    @Column(name="date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date taskDate;

    @ManyToOne
    @JsonIgnore
    private User user;

}
