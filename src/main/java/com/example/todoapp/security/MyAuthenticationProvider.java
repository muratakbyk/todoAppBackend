package com.example.todoapp.security;

import com.example.todoapp.core.constants.Messages.Messages;
import com.example.todoapp.entities.User;
import com.example.todoapp.repo.UserRepository;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.Collections;


@Component
public class MyAuthenticationProvider implements AuthenticationProvider {



    @Override
    public Authentication authenticate(final Authentication authentication) throws AuthenticationException {

        final UsernamePasswordAuthenticationToken userNamePasswordToken
                = (UsernamePasswordAuthenticationToken) authentication;

        final Object principal = authentication.getPrincipal();
        final UsernamePasswordAuthenticationToken result = new UsernamePasswordAuthenticationToken(
                principal, authentication.getCredentials(),
                Collections.emptyList());
        result.setDetails(authentication.getDetails());
        return result;
    }

    @Override
    public boolean supports(final Class<?> authentication) {
        return true;
    }
}