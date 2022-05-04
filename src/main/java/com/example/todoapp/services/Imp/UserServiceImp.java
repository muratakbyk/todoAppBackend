package com.example.todoapp.services.Imp;

import com.example.todoapp.core.constants.Messages.Messages;
import com.example.todoapp.core.utilities.*;
import com.example.todoapp.dto.UserCreateRequest;
import com.example.todoapp.dto.UserLoginRequest;
import com.example.todoapp.entities.User;
import com.example.todoapp.repo.UserRepository;
import com.example.todoapp.services.abstracts.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceImp implements UserService {

   @Autowired
   private UserRepository userRepository;

    @Autowired
   private  PasswordEncoder passwordEncoder;

    @Override
    public Result login(UserLoginRequest userLoginRequest) {

        Authentication authentication =
                new UsernamePasswordAuthenticationToken(userLoginRequest.getEmail(),
                userLoginRequest.getPassword());
        User currentUser = findUserByEmail(userLoginRequest.getEmail()).getData();
        if(!passwordEncoder.matches(userLoginRequest.getPassword(),currentUser.getPassword())){
            return new ErrorResult(Messages.wrongPassword);
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
            return new SuccessResult(Messages.successfulLogin);
    }

    @Override
    public Result register(UserCreateRequest userCreateRequest) {

       if(userRepository.findByEmail(userCreateRequest.getEmail()).isPresent()){
            return new ErrorResult(Messages.existUser);
        }
        User user = new User();
        user.setName(userCreateRequest.getName());
        user.setSurname(userCreateRequest.getSurname());
        user.setEmail(userCreateRequest.getEmail());
        user.setUserName(userCreateRequest.getUserName());
        user.setPassword(encodePassword(userCreateRequest.getPassword()));
        userRepository.save(user);
        return new SuccessResult(Messages.userRegistered);
    }

    @Override
    public DataResult<User> findUserByEmail(String email) {
        User user = userRepository.findByEmail(email).get();
        if(user == null){
            return new ErrorDataResult<User>(Messages.userNotFound);
        }
            return new SuccessDataResult<User>(user);
    }
    @Override
    public String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

}
