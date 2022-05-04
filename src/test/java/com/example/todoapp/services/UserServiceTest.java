package com.example.todoapp.services;

import com.example.todoapp.dto.UserCreateRequest;
import com.example.todoapp.dto.UserLoginRequest;
import com.example.todoapp.entities.User;
import com.example.todoapp.repo.UserRepository;
import com.example.todoapp.services.abstracts.UserService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserServiceTest {

    @Autowired
    UserService userService;

    UserLoginRequest userLoginRequest;
    UserCreateRequest userCreateRequest;

    String testMail = "testmail@test.com";
    String testPassword = "testPassword";

    @BeforeAll
    void setUp(){

        userCreateRequest = new UserCreateRequest();
        userCreateRequest.setName("testName");
        userCreateRequest.setSurname("testSurname");
        userCreateRequest.setUserName("testUsername2");
        userCreateRequest.setEmail(testMail);
        userCreateRequest.setPassword(testPassword);

        userLoginRequest = new UserLoginRequest();
        userLoginRequest.setEmail(testMail);
        userLoginRequest.setPassword(testPassword);


    }

    @Test
    @Order(2)
    void loginTest(){
       assertEquals(userService.login(userLoginRequest).isSuccess(),true);

    }

    @Test
    @Order(1)
    void registerTest(){
        assertEquals(userService.register(userCreateRequest).isSuccess(),true);

    }
}
