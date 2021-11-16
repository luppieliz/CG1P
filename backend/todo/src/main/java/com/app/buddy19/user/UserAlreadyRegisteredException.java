package com.app.buddy19.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserAlreadyRegisteredException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public UserAlreadyRegisteredException(UUID userId) {
        super("User with ID: " + userId + " is already registered!");
    }

    public UserAlreadyRegisteredException(String email) {
        super("User " + email + " is already registered!");
    }
}