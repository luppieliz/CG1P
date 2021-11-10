package com.app.todo.todo;

import com.app.todo.user.User;
import com.app.todo.user.UserNotFoundException;
import com.app.todo.user.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoServiceImpl {
    private TodoRepository todoRepository;
    private UserService userService;

    @Autowired
    public TodoServiceImpl(TodoRepository todoRepository, UserService userService) {
        this.todoRepository = todoRepository;
        this.userService = userService;
    }

    /**
     * Get a specific user's to-do list
     * @param userId
     * @return A list of to-dos of a user with a given userID
     */
    public List<Todo> getTodosByUserId(Long userId) {
        return todoRepository.findByCreatedById(userId);
    }

    /**
     * Get a specific to-do with a given todoID
     * @param todoId
     * @return A to-do with a given todoID. If a to-do is not found, throw a TodoNotFoundException.
     * @throws TodoNotFoundException
     */
    public Todo getTodo(Long todoId) throws TodoNotFoundException {
        return todoRepository.findById(todoId).orElseThrow(() -> new TodoNotFoundException(todoId));
    }

    /**
     * Add a user's to-do.
     * @param userId
     * @param newTodo
     * @return A newly added to-do by a user with a given userID. If a user is not found, throw a UserNotFoundException.
     * @throws UserNotFoundException
     */
    public Todo addTodo(Long userId, Todo newTodo) throws UserNotFoundException {
        User owner = userService.getUser(userId);
        List<User> assignedUsers = userService.getAllUsersById(newTodo.getCreatedForIds());

        newTodo.setCreatedBy(owner);
        newTodo.setCreatedFor(assignedUsers);

        return todoRepository.save(newTodo);
    }

    /**
     * Update a specific to-do with a given todoID.
     * @param todoId
     * @param newTodo
     * @throws TodoNotFoundException
     */
    public void updateTodo(Long todoId, Todo newTodo) throws TodoNotFoundException {
        Todo foundTodo = getTodo(todoId);
        List<Long> assignedIds = newTodo.getCreatedForIds();
        List<User> assignedUsers = userService.getAllUsersById(assignedIds);

        foundTodo.setDescription(newTodo.getDescription());
        foundTodo.setCreatedForIds(assignedIds);
        foundTodo.setCreatedFor(assignedUsers);

        todoRepository.save(foundTodo);
    }

    /**
     * Delete a specific to-do with a given todoID.
     * @param todoId
     */
    public void deleteTodo(Long todoId) {
        todoRepository.deleteById(todoId);
    }
}
