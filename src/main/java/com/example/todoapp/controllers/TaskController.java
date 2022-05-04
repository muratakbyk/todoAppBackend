package com.example.todoapp.controllers;

import com.example.todoapp.core.utilities.DataResult;
import com.example.todoapp.core.utilities.Result;
import com.example.todoapp.dto.TaskCreateRequest;
import com.example.todoapp.dto.TaskUpdateRequest;
import com.example.todoapp.entities.Task;
import com.example.todoapp.services.abstracts.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="/task/")

public class TaskController {

    @Autowired
    TaskService taskService;

    @PostMapping("create")
    public Result createTask(@RequestBody TaskCreateRequest taskCreateRequest){
        return taskService.createTask(taskCreateRequest);
    }

    @GetMapping("getall")
    public DataResult<List<Task>> getTasks(){
        return taskService.getAllTasks();
    }

    @PostMapping("delete")
    public Result deleteTask(@RequestParam long id){
        return taskService.delete(id);

    }

    @PostMapping("update")
    public Result updateTask(@RequestParam long id, @RequestBody TaskUpdateRequest taskUpdateRequest){
        return taskService.Update(id,taskUpdateRequest);
    }

}
