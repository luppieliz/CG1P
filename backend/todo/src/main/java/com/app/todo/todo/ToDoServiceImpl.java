package com.app.todo.todo;

import com.app.todo.user.UserNotFoundException;
import com.app.todo.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ToDoServiceImpl {
    private ToDoRepository todoRepository;
    private UserRepository userRepository;

    @Autowired
    public ToDoServiceImpl(ToDoRepository todoRepository, UserRepository userRepository) {
        this.todoRepository = todoRepository;
        this.userRepository = userRepository;
    }

    public List<ToDo> getTodosByUserId(Long userId) {
        return todoRepository.findByUserId(userId);
    }

    public ToDo getTodo(Long todoId) throws ToDoNotFoundException {
        return todoRepository.findById(todoId).orElseThrow(() -> new ToDoNotFoundException(todoId));
    }

    public ToDo addTodo(Long userId, ToDo newTodo) {
        return userRepository.findById(userId).map(foundUser -> {
            newTodo.setUser(foundUser);
            return todoRepository.save(newTodo);
        }).orElseThrow(() -> new UserNotFoundException(userId));
    }

    public void updateTodo(Long todoId, ToDo newTodo) throws ToDoNotFoundException {
        todoRepository.findById(todoId).map(foundTodo -> {
            foundTodo.setDescription(newTodo.getDescription());
            return todoRepository.save(foundTodo);
        }).orElseThrow(() -> new ToDoNotFoundException(todoId));
    }

    public void deleteTodo(Long todoId) {
        todoRepository.deleteById(todoId);
    }
}