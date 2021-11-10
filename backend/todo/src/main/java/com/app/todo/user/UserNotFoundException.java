package com.app.todo.user;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public UserNotFoundException(UUID userId) {
        super("User with ID: " + userId + " is not registered!");
    }

    public UserNotFoundException(String email) {
        super("User " + email + " is not registered!");
    }
}