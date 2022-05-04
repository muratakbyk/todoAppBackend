package com.example.todoapp.services;


import com.example.todoapp.dto.TaskCreateRequest;
import com.example.todoapp.dto.TaskUpdateRequest;
import com.example.todoapp.entities.Task;
import com.example.todoapp.entities.User;
import com.example.todoapp.repo.TaskRepository;
import com.example.todoapp.repo.UserRepository;
import com.example.todoapp.services.Imp.TaskServiceImp;
import com.example.todoapp.services.Imp.UserServiceImp;
import com.example.todoapp.services.abstracts.TaskService;
import com.example.todoapp.services.abstracts.UserService;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Date;


import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TaskServiceTest {

    @Autowired
    private TaskService taskService;
    @Autowired
    private UserService userService;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    Task task1;
    TaskCreateRequest taskCreateRequest;
    User user;


    @BeforeAll
    void setUp(){

        user = new User();
        user.setName("testName");
        user.setEmail("testemail@test.com");
        user.setUserName("testUsername");
        user.setPassword(userService.encodePassword("testPassword"));
        userRepository.save(user);

        taskCreateRequest = new TaskCreateRequest();
        taskCreateRequest.setTaskName("testName");
        taskCreateRequest.setTaskContent("testContent");
        taskCreateRequest.setTaskDate(new Date());


        task1 = new Task();
        task1.setTaskName("taskName");
        task1.setContent("taskContent");
        task1.setTaskDate(new Date());
        task1.setDone(false);
        task1.setUser(user);
        taskRepository.save(task1);

    }

    @Test
   @Order(2)
    public void createTask(){
        Authentication authentication1 =
                new UsernamePasswordAuthenticationToken(user.getEmail(), "testPassword");
        SecurityContextHolder.getContext().setAuthentication(authentication1);

        assertEquals(taskService.getAllTasks().getData().size(),1);
        taskService.createTask(taskCreateRequest);
        assertEquals(taskService.getAllTasks().getData().size(),2);
        taskService.createTask(taskCreateRequest);
        assertEquals(taskService.getAllTasks().getData().size(),3);

    }

    @Test
    @Order(1)
    public void getAllTask(){
        Authentication authentication1 =
                new UsernamePasswordAuthenticationToken(user.getEmail(), "testPassword");
        SecurityContextHolder.getContext().setAuthentication(authentication1);
        assertEquals(taskService.getAllTasks().getData().size(),1);
        assertNotNull(taskService.getAllTasks().getData());
    }




}
