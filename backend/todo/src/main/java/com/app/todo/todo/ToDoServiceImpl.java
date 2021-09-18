package com.app.todo.todo;

import com.app.todo.todo.ToDo;
import com.app.todo.user.UserNotFoundException;
import com.app.todo.user.UserRepository;
import com.app.todo.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ToDoServiceImpl {
    private ToDoRepository toDoRepository;
    private UserRepository userRepository;

    @Autowired
    public ToDoServiceImpl(ToDoRepository toDoRepository, UserRepository userRepository) {
        this.toDoRepository = toDoRepository;
        this.userRepository = userRepository;
    }

    public boolean validateUser(Long userId) {
        return userRepository.existsById(userId);
    }

    public ToDo getSpecificToDo(Long userId, Long toDoId) {
        if (!validateUser(userId)) {
            throw new UserNotFoundException(userId);
        }

        ToDo toDo = toDoRepository.findById(toDoId).orElseThrow(() -> new ToDoNotFoundException(toDoId));
        return toDo;
    }

    public void deleteSpecificToDo(Long userId, Long toDoId) {
        if (!validateUser(userId)) {
            throw new UserNotFoundException(userId);
        }

        toDoRepository.deleteById(toDoId);
    }

    public List<ToDo> findToDoByUserId(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException(userId);
        }
        return toDoRepository.findByUserId(userId);
    }

    public void updateSpecificToDo(Long userId, Long toDoId, ToDo newToDo) {
        if (!validateUser(userId)) {
            throw new UserNotFoundException(userId);
        }

        toDoRepository.findByIdAndUserId(toDoId, userId).map(
                foundToDo -> {
                    foundToDo.setDescription(newToDo.getDescription());
                    return toDoRepository.save(foundToDo);
                }).orElseThrow(() -> new ToDoNotFoundException(toDoId));
    }

    public ToDo addToDo(Long userId, ToDo newToDo) {
        return userRepository.findById(userId).map(user -> {
            newToDo.setUser(user);
            return toDoRepository.save(newToDo);
        }).orElseThrow(() -> new UserNotFoundException(userId));
    }
}