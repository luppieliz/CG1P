package com.app.todo.todo;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class TodoNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public TodoNotFoundException(UUID todoId) {
        super("Todo with ID:" + todoId + " not found!");
    }
}
