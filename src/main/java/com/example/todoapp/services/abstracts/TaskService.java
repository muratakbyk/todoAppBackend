package com.example.todoapp.services.abstracts;

import com.example.todoapp.core.utilities.DataResult;
import com.example.todoapp.core.utilities.Result;
import com.example.todoapp.dto.TaskCreateRequest;
import com.example.todoapp.dto.TaskUpdateRequest;
import com.example.todoapp.entities.Task;

import java.util.List;

public interface TaskService extends BaseEntityService {

    Result createTask(TaskCreateRequest taskCreateRequest);
    Result delete(long id);
    DataResult<List<Task>> getAllTasks();
    Result Update(long taskId, TaskUpdateRequest taskUpdateRequest);
}
