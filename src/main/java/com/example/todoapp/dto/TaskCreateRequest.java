package com.example.todoapp.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
public class TaskCreateRequest {

    @NotBlank
    private String taskName;

    @NotBlank
    private String taskContent;

    @NotBlank
    private Date taskDate;






}
