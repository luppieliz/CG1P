package com.app.buddy19.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

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