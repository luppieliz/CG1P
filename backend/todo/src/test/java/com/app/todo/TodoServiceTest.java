package com.app.todo;

import com.app.todo.business.Business;
import com.app.todo.todo.ToDo;
import com.app.todo.todo.ToDoRepository;
import com.app.todo.todo.ToDoServiceImpl;
import com.app.todo.user.User;
import com.app.todo.user.UserRepository;
import com.app.todo.user.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TodoServiceTest {

    @Mock
    private ToDoRepository toDoRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder encoder;

    @InjectMocks
    private ToDoServiceImpl toDoService;

    @InjectMocks
    private UserService userService;


    @Test
    void addTodo_NewToDo_ReturnSavedTodo() {
        // User user = new User("alice@gmail.com", "Alice", "password1", "ROLE_BUSINESSOWNER", new Business("fda"));
        // userService.addUser(user);

        // ToDo todo = new ToDo("Submit Vaccination Status");

        // when(userRepository.findByEmail(any(String.class))).thenReturn(Optional.of(user));
        // when(toDoRepository.save(any (ToDo.class))).thenReturn(todo);

        // ToDo savedTodo = toDoService.addToDo(user.getEmail(),todo);
        // assertNotNull(savedTodo);

        // verify(userRepository).findByEmail(user.getEmail());
        // verify(toDoRepository).save(todo);

    }


}
