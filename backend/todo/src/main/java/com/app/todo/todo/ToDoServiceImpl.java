package com.app.todo.todo;

import com.app.todo.todo.ToDo;
import com.app.todo.user.User;
import com.app.todo.user.UserNotFoundException;
import com.app.todo.user.UserRepository;
import com.app.todo.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ToDoServiceImpl {
    private ToDoRepository toDoRepository;
    private UserRepository userRepository;

    @Autowired
    public ToDoServiceImpl(ToDoRepository toDoRepository, UserRepository userRepository) {
        this.toDoRepository = toDoRepository;
        this.userRepository = userRepository;
    }

    public boolean validateUser(String username) {
        Optional<User> foundUser = userRepository.findByUsername(username);
        if (foundUser.isEmpty()) {
            return false;
        }
        return true;
    }

    public ToDo getSpecificToDo(String username, Long toDoId) {
        if (!validateUser(username)) {
            throw new UserNotFoundException(username);
        }

        ToDo toDo = toDoRepository.findById(toDoId).orElseThrow(() -> new ToDoNotFoundException(toDoId));
        return toDo;
    }

    public void deleteSpecificToDo(String username, Long toDoId) {
        if (!validateUser(username)) {
            throw new UserNotFoundException(username);
        }

        toDoRepository.deleteById(toDoId);
    }

    public List<ToDo> findToDoByUsername(String username) {
        if (!validateUser(username)) {
            throw new UserNotFoundException(username);
        }
        Optional<User> foundUser = userRepository.findByUsername(username);

        return toDoRepository.findByUserId(foundUser.get().getId());
    }

    public void updateSpecificToDo(String username, Long toDoId, ToDo newToDo) {
        if (!validateUser(username)) {
            throw new UserNotFoundException(username);
        }
        Optional<User> foundUser = userRepository.findByUsername(username);

        toDoRepository.findByIdAndUserId(toDoId, foundUser.get().getId()).map(
                foundToDo -> {
                    foundToDo.setDescription(newToDo.getDescription());
                    return toDoRepository.save(foundToDo);
                }).orElseThrow(() -> new ToDoNotFoundException(toDoId));
    }

    public ToDo addToDo(String username, ToDo newToDo) {
        return userRepository.findByUsername(username).map(user -> {
            newToDo.setUser(user);
            return toDoRepository.save(newToDo);
        }).orElseThrow(() -> new UserNotFoundException(username));
    }
}