package com.example.todoapp.controller;

import com.example.todoapp.core.utilities.Result;
import com.example.todoapp.dto.TaskUpdateRequest;
import com.example.todoapp.dto.UserCreateRequest;
import com.example.todoapp.dto.UserLoginRequest;
import com.example.todoapp.entities.User;
import com.example.todoapp.repo.UserRepository;
import com.example.todoapp.services.abstracts.UserService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    UserCreateRequest userCreateRequest;
    UserLoginRequest userLoginRequest;
    User user;
    HttpHeaders headers = new HttpHeaders();


    private String url(String method) {
        StringBuilder testUrl = new StringBuilder();
        testUrl.append("http://localhost:");
        testUrl.append(port);
        testUrl.append("/user/");
        testUrl.append(method);
        return testUrl.toString();
    }

    @BeforeAll
    void setUp(){
        userCreateRequest = new UserCreateRequest();
        userCreateRequest.setUserName("testUsername");
        userCreateRequest.setName("testName");
        userCreateRequest.setSurname("testSurname");
        userCreateRequest.setEmail("eMail");
        userCreateRequest.setPassword("testPass");

        User user = new User();
        user.setName(userCreateRequest.getName());
        user.setSurname(userCreateRequest.getSurname());
        user.setUserName(userCreateRequest.getUserName());
        user.setEmail(userCreateRequest.getEmail());
        user.setPassword(userService.encodePassword(userCreateRequest.getPassword()));
        userRepository.save(user);

        userLoginRequest = new UserLoginRequest();
        userLoginRequest.setEmail(userCreateRequest.getEmail());
        userLoginRequest.setPassword(userCreateRequest.getPassword());


    }

    @Test
    @Order(1)
    public void register(){
        String newUrl = url("register");
        HttpEntity<UserCreateRequest> request = new HttpEntity<>(userCreateRequest,headers);
        ResponseEntity<Result> responseEntity =
                restTemplate.postForEntity(newUrl, userCreateRequest,Result.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    @Order(2)
    public void login(){
        String newUrl = url("login");
        HttpEntity<UserLoginRequest> request = new HttpEntity<>(userLoginRequest,headers);
        ResponseEntity<Result> responseEntity =
                restTemplate.postForEntity(newUrl, userLoginRequest,Result.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
}
