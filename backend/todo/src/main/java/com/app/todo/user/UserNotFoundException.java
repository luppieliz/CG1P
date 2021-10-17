package com.app.todo.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public UserNotFoundException(Long userId) {
        super("User with ID:" + userId + " is not registered!");
    }

    public UserNotFoundException(String email) {
        super("Email" + email + " is not registered!");
    }
}