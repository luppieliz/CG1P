package com.app.todo.todo;

import com.app.todo.user.UserNotFoundException;

import java.util.List;
import java.util.UUID;

public interface TodoService {
    List<Todo> getCreatedTodos(UUID userId);

    List<Todo> getAssignedTodos(UUID userId);

    Todo getTodo(UUID todoId) throws TodoNotFoundException;

    Todo addTodo(UUID userId, Todo newTodo) throws UserNotFoundException;

    void updateTodo(UUID todoId, Todo newTodo) throws TodoNotFoundException;

    void deleteTodo(UUID todoId);
}
