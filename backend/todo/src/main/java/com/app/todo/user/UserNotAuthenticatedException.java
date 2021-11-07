package com.app.todo.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UserNotAuthenticatedException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public UserNotAuthenticatedException() {
        super("Authentication failed - invalid credentials!");
    }
}