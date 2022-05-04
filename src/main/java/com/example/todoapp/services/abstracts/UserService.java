package com.example.todoapp.services.abstracts;

import com.example.todoapp.core.utilities.DataResult;
import com.example.todoapp.core.utilities.Result;
import com.example.todoapp.dto.UserCreateRequest;
import com.example.todoapp.dto.UserLoginRequest;
import com.example.todoapp.entities.User;

public interface UserService extends BaseEntityService<User> {

    Result login(UserLoginRequest userLoginRequest);
    Result register(UserCreateRequest userCreateRequest);
    DataResult<User> findUserByEmail(String email);
    String encodePassword(String password);

}
