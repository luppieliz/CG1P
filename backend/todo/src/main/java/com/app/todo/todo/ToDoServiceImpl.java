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

    /**
     * Get a specific user's to-do list
     * @param userId
     * @return A list of to-dos of a user with a given userID
     */
    public List<ToDo> getTodosByUserId(Long userId) {
        return todoRepository.findByUserId(userId);
    }

    /**
     * Get a specific to-do with a given todoID
     * @param todoId
     * @return A to-do with a given todoID. If a to-do is not found, throw an ToDoNotFoundException.
     * @throws ToDoNotFoundException
     */
    public ToDo getTodo(Long todoId) throws ToDoNotFoundException {
        return todoRepository.findById(todoId).orElseThrow(() -> new ToDoNotFoundException(todoId));
    }

    /**
     * Add a user's to-do.
     * @param userId
     * @param newTodo
     * @return A newly added to-do by a user with a given userID. If a user is not found, throw an UserNotFoundException.
     */
    public ToDo addTodo(Long userId, ToDo newTodo) {
        return userRepository.findById(userId).map(foundUser -> {
            newTodo.setUser(foundUser);
            return todoRepository.save(newTodo);
        }).orElseThrow(() -> new UserNotFoundException(userId));
    }

    /**
     * Update a specific to-do with a given todoID.
     * @param todoId
     * @param newTodo
     * @throws ToDoNotFoundException
     */
    public void updateTodo(Long todoId, ToDo newTodo) throws ToDoNotFoundException {
        todoRepository.findById(todoId).map(foundTodo -> {
            foundTodo.setDescription(newTodo.getDescription());
            return todoRepository.save(foundTodo);
        }).orElseThrow(() -> new ToDoNotFoundException(todoId));
    }

    /**
     * Delete a specific to-do with a given todoID.
     * @param todoId
     */
    public void deleteTodo(Long todoId) {
        todoRepository.deleteById(todoId);
    }
}