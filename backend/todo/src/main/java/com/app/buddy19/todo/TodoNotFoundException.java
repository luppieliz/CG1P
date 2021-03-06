package com.app.buddy19.todo;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class TodoNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public TodoNotFoundException(UUID todoId) {
        super("Todo with ID:" + todoId + " not found!");
    }
}
