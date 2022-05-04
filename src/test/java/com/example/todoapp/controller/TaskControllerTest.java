package com.example.todoapp.controller;
import com.example.todoapp.dto.TaskUpdateRequest;
import com.example.todoapp.entities.Task;
import com.example.todoapp.repo.TaskRepository;
import com.example.todoapp.core.utilities.Result;
import com.example.todoapp.dto.TaskCreateRequest;
import com.example.todoapp.dto.UserLoginRequest;
import com.example.todoapp.entities.User;
import com.example.todoapp.repo.UserRepository;
import com.example.todoapp.services.abstracts.UserService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;


import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TaskControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskRepository taskRepository;

    HttpHeaders headers = new HttpHeaders();
    TaskCreateRequest taskCreateRequest;
    UserLoginRequest userLoginRequest;
    TaskUpdateRequest taskUpdateRequest;
    Task task;

    private String url(String method) {
        StringBuilder testUrl = new StringBuilder();
        testUrl.append("http://localhost:");
        testUrl.append(port);
        testUrl.append("/task/");
        testUrl.append(method);
        return testUrl.toString();
    }
    @BeforeAll
    void setUp(){

        User user = new User();
        user.setName("testName");
        user.setSurname("testSurname");
        user.setUserName("testUsername");
        user.setEmail("testEmail@test.com");
        user.setPassword(userService.encodePassword("testPassword"));
        userRepository.save(user);

        userLoginRequest = new UserLoginRequest();
        userLoginRequest.setEmail(user.getEmail());
        userLoginRequest.setPassword("testPassword");

        taskCreateRequest = new TaskCreateRequest();
        taskCreateRequest.setTaskName("testTask");
        taskCreateRequest.setTaskContent("testContent");
        taskCreateRequest.setTaskDate(new Date());

        task = new Task();
        task.setUser(user);
        task.setTaskName("testName");
        task.setContent("testContent");
        task.setTaskDate(new Date());
        task.setDone(true);
        taskRepository.save(task);

        taskUpdateRequest = new TaskUpdateRequest();
        taskUpdateRequest.setTaskContent(task.getContent());
        taskUpdateRequest.setTaskName(task.getTaskName());
        taskUpdateRequest.setTaskDate(task.getTaskDate());
        taskUpdateRequest.setDidOrNot(task.isDone());
    }



    @Test
    @Order(1)
    public void testCreateTask(){
        String newUrl = url("create");

        HttpEntity<TaskCreateRequest> request = new HttpEntity<>(taskCreateRequest,headers);

        ResponseEntity<Result> responseEntity =
                restTemplate.withBasicAuth(userLoginRequest.getEmail(),userLoginRequest.getPassword()).postForEntity(
                        newUrl,request,Result.class
                        );
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    @Order(2)
    public void getAll(){
        String newUrl = url("getall");
        ResponseEntity<Result> responseEntity =
                restTemplate.withBasicAuth(userLoginRequest.getEmail(),userLoginRequest.getPassword()).
                        getForEntity(newUrl, Result.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }


    @Test
    @Order(4)
    public void delete(){
        String newUrl = url("delete?id={id}");

        ResponseEntity<Result> responseEntity =
                restTemplate.withBasicAuth(userLoginRequest.getEmail(),userLoginRequest.getPassword()).
                        postForEntity(newUrl, null,Result.class,
                                task.getId());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    @Order(3)
    public void update(){

        String newUrl = url("update?id={id}");

        HttpEntity<TaskUpdateRequest> request = new HttpEntity<>(taskUpdateRequest,headers);
        ResponseEntity<Result> responseEntity =
                restTemplate.withBasicAuth(userLoginRequest.getEmail(),userLoginRequest.getPassword()).
                        postForEntity(newUrl, taskUpdateRequest,Result.class,
                                task.getId());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }




}
