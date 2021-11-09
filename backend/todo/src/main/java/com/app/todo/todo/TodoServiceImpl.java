package com.app.todo.todo;

import com.app.todo.user.UserNotFoundException;
import com.app.todo.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoServiceImpl {
    private TodoRepository todoRepository;
    private UserRepository userRepository;

    @Autowired
    public TodoServiceImpl(TodoRepository todoRepository, UserRepository userRepository) {
        this.todoRepository = todoRepository;
        this.userRepository = userRepository;
    }

    /**
     * Get a specific user's to-do list
     * @param userId
     * @return A list of to-dos of a user with a given userID
     */
    public List<Todo> getTodosByUserId(Long userId) {
        return todoRepository.findByUserId(userId);
    }

    /**
     * Get a specific to-do with a given todoID
     * @param todoId
     * @return A to-do with a given todoID. If a to-do is not found, throw an TodoNotFoundException.
     * @throws TodoNotFoundException
     */
    public Todo getTodo(Long todoId) throws TodoNotFoundException {
        return todoRepository.findById(todoId).orElseThrow(() -> new TodoNotFoundException(todoId));
    }

    /**
     * Add a user's to-do.
     * @param userId
     * @param newTodo
     * @return A newly added to-do by a user with a given userID. If a user is not found, throw an UserNotFoundException.
     * @throws UserNotFoundException
     */
    public Todo addTodo(Long userId, Todo newTodo) throws UserNotFoundException {
        return userRepository.findById(userId).map(foundUser -> {
            newTodo.setUser(foundUser);
            return todoRepository.save(newTodo);
        }).orElseThrow(() -> new UserNotFoundException(userId));
    }

    /**
     * Update a specific to-do with a given todoID.
     * @param todoId
     * @param newTodo
     * @throws TodoNotFoundException
     */
    public void updateTodo(Long todoId, Todo newTodo) throws TodoNotFoundException {
        todoRepository.findById(todoId).map(foundTodo -> {
            foundTodo.setDescription(newTodo.getDescription());
            return todoRepository.save(foundTodo);
        }).orElseThrow(() -> new TodoNotFoundException(todoId));
    }

    /**
     * Delete a specific to-do with a given todoID.
     * @param todoId
     */
    public void deleteTodo(Long todoId) {
        todoRepository.deleteById(todoId);
    }
}
