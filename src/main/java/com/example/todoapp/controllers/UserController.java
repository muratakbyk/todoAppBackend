package com.example.todoapp.controllers;
import com.example.todoapp.core.utilities.Result;
import com.example.todoapp.dto.UserCreateRequest;
import com.example.todoapp.dto.UserLoginRequest;
import com.example.todoapp.services.abstracts.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value="/user/")

public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("register")
    public Result registerUser(@RequestBody UserCreateRequest userCreateRequest){
        return userService.register(userCreateRequest);
    }

    @PostMapping("login")
    public Result loginUser(@RequestBody UserLoginRequest userLoginRequest){
        return userService.login(userLoginRequest);
    }
}
