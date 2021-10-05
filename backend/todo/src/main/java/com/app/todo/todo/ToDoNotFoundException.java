package com.app.todo.todo;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ToDoNotFoundException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public ToDoNotFoundException(Long toDoId) {
        super("User with ID:" + toDoId + " is not registered!");
    }
}
