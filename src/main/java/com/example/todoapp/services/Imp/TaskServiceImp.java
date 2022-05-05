package com.example.todoapp.services.Imp;

import com.example.todoapp.core.constants.Messages.Messages;
import com.example.todoapp.core.utilities.*;
import com.example.todoapp.dto.TaskCreateRequest;
import com.example.todoapp.dto.TaskUpdateRequest;
import com.example.todoapp.entities.Task;
import com.example.todoapp.entities.User;
import com.example.todoapp.repo.TaskRepository;
import com.example.todoapp.repo.UserRepository;
import com.example.todoapp.services.abstracts.TaskService;
import com.example.todoapp.services.abstracts.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class TaskServiceImp implements TaskService {

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    UserService userService;

    @Override
    public Result createTask(TaskCreateRequest taskCreateRequest) {

        Task task = new Task();
        task.setTaskName(taskCreateRequest.getTaskName());
        task.setContent(taskCreateRequest.getTaskContent());
        task.setTaskDate(taskCreateRequest.getTaskDate());
        task.setUser(getCurrentUser().getData());
        taskRepository.save(task);
        return new SuccessResult(Messages.taskCreated);

    }

    @Override
    public DataResult<List<Task>> getAllTasks() {
        return new SuccessDataResult<List<Task>>(taskRepository.findAllByUser
               (getCurrentUser().getData()),Messages.tasksListed);
    }

    @Override
    public Result Update(long taskId, TaskUpdateRequest taskUpdateRequest) {

          if(taskBelongUser(taskId).isSuccess()){
            Task task = taskRepository.getById(taskId);
            task.setTaskName(taskUpdateRequest.getTaskName());
            task.setContent(taskUpdateRequest.getTaskContent());
            task.setDone(taskUpdateRequest.isDidOrNot());
            task.setTaskDate(taskUpdateRequest.getTaskDate());
            taskRepository.save(task);
            return new SuccessResult(Messages.taskUpdated);
        }
        return new ErrorResult(Messages.authorityOrTaskError);
    }

    @Override
    public Result delete(long id) {
        if(taskBelongUser(id).isSuccess()){
            taskRepository.deleteById(id);
            return new SuccessResult(Messages.taskDeleted);
         }
        return new ErrorResult(Messages.authorityOrTaskError);
    }

    public DataResult<User> getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(authentication.getName()).getData();
        return new SuccessDataResult<User>(user);
    }

    //Here we check whether the task that is requested to be updated or deleted belongs to current user or not.
    public Result taskBelongUser(long taskId){
        if(getAllTasks().getData().contains(taskRepository.getById(taskId))){
            return new SuccessResult();
        }
        return new ErrorResult();
    }


}
