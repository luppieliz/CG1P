package com.app.buddy19.todo;

import com.app.buddy19.user.User;
import com.app.buddy19.user.UserNotFoundException;
import com.app.buddy19.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TodoServiceImpl implements TodoService {
    private TodoRepository todoRepository;
    private UserService userService;

    @Autowired
    public TodoServiceImpl(TodoRepository todoRepository, UserService userService) {
        this.todoRepository = todoRepository;
        this.userService = userService;
    }

    /**
     * Get a business owner's created to-dos
     *
     * @param userId
     * @return A list of to-dos of a user with a given userID
     */
    @Override
    public List<Todo> getCreatedTodos(UUID userId) {
        return todoRepository.findByCreatedBy_Id(userId);
    }

    /**
     * Get an employee's assigned to-dos
     *
     * @param userId
     * @return A list of to-dos of a user with a given userID
     */
    @Override
    public List<Todo> getAssignedTodos(UUID userId) {
        return todoRepository.findByCreatedFor_Id(userId);
    }

    /**
     * Get a specific to-do with a given todoID
     *
     * @param todoId
     * @return A to-do with a given todoID. If a to-do is not found, throw a TodoNotFoundException.
     * @throws TodoNotFoundException
     */
    @Override
    public Todo getTodo(UUID todoId) throws TodoNotFoundException {
        return todoRepository.findById(todoId)
                             .orElseThrow(() -> new TodoNotFoundException(todoId));
    }

    /**
     * Add a user's to-do.
     *
     * @param userId
     * @param newTodo
     * @return A newly added to-do by a user with a given userID. If a user is not found, throw a UserNotFoundException.
     * @throws UserNotFoundException
     */
    @Override
    public Todo addTodo(UUID userId, Todo newTodo) throws UserNotFoundException {
        User owner = userService.getUser(userId);
        List<User> assignedUsers = userService.getAllUsersById(newTodo.getCreatedForIds());

        newTodo.setCreatedBy(owner);
        newTodo.setCreatedFor(assignedUsers);

        return todoRepository.save(newTodo);
    }

    /**
     * Update a specific to-do with a given todoID.
     *
     * @param todoId
     * @param newTodo
     * @throws TodoNotFoundException
     */
    @Override
    public void updateTodo(UUID todoId, Todo newTodo) throws TodoNotFoundException {
        Todo foundTodo = getTodo(todoId);
        List<UUID> assignedIds = newTodo.getCreatedForIds();
        List<User> assignedUsers = userService.getAllUsersById(assignedIds);

        foundTodo.setDescription(newTodo.getDescription());
        foundTodo.setTargetDate(newTodo.getTargetDate());
        foundTodo.setCreatedForIds(assignedIds);
        foundTodo.setCreatedFor(assignedUsers);

        todoRepository.save(foundTodo);
    }

    /**
     * Delete a specific to-do with a given todoID.
     *
     * @param todoId
     */
    @Override
    public void deleteTodo(UUID todoId) {
        todoRepository.deleteById(todoId);
    }
}
