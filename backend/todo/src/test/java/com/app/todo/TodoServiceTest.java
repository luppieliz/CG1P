package com.app.todo;

import com.app.todo.business.Business;
import com.app.todo.todo.Todo;
import com.app.todo.todo.TodoRepository;
import com.app.todo.todo.TodoServiceImpl;
import com.app.todo.user.User;
import com.app.todo.user.UserRepository;
import com.app.todo.user.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TodoServiceTest {

    @Mock
    private TodoRepository todoRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder encoder;

    @InjectMocks
    private TodoServiceImpl todoService;

    @Mock
    private UserService userService;

    @Test
    void getCreatedTodos_ValidUserId_ReturnTodos() {
        UUID userID = UUID.randomUUID();
        User user = new User(userID,"alice@gmail.com", "Alice", "password1", "ROLE_BUSINESSOWNER", new Business("fda"));

        Todo todo = new Todo(user,Date.valueOf(LocalDate.now()), "Submit Vaccination Status");

        List<Todo> todoList = new ArrayList<>();
        todoList.add(todo);

        when(todoRepository.findByCreatedBy_Id(any(UUID.class))).thenReturn(todoList);

        List<Todo> foundTodo = todoService.getCreatedTodos(user.getId());

        assertNotNull(foundTodo);
        verify(todoRepository).findByCreatedBy_Id(user.getId());
    }

    @Test
    void getAssignedTodos_ValidUserId_ReturnTodo() {
        UUID ownerID = UUID.randomUUID();
        User owner = new User(ownerID,"alice@gmail.com", "Alice", "password1", "ROLE_BUSINESSOWNER", new Business("fda"));

        List<UUID> employeeIDs = new ArrayList<>();
        UUID employeeID1 = UUID.randomUUID();
        UUID employeeID2 = UUID.randomUUID();
        employeeIDs.add(employeeID1);
        employeeIDs.add(employeeID2);

        Todo todo1 = new Todo(owner,Date.valueOf(LocalDate.now()), "Submit Vaccination Status", employeeIDs);
        Todo todo2 = new Todo(owner,Date.valueOf(LocalDate.now()), "Upload schedule", employeeIDs);

        List<Todo> todoList = new ArrayList<>();
        todoList.add(todo1);
        todoList.add(todo2);

        when(todoRepository.findByCreatedFor_Id(any(UUID.class))).thenReturn(todoList);

        List<Todo> foundTodoList1 = todoService.getAssignedTodos(employeeID1);
        List<Todo> foundTodoList2 = todoService.getAssignedTodos(employeeID2);

        assertNotNull(foundTodoList1);
        assertNotNull(foundTodoList2);
        assertEquals(foundTodoList1, foundTodoList2);
        verify(todoRepository).findByCreatedFor_Id(employeeID1);
        verify(todoRepository).findByCreatedFor_Id(employeeID2);
    }

    @Test
    void getToDo_Success() {
        UUID userID1 = UUID.randomUUID();
        UUID userID2 = UUID.randomUUID();

        List<UUID> userIDList = new ArrayList<>();
        userIDList.add(userID1);
        userIDList.add(userID2);

        UUID todoID = UUID.randomUUID();
        Todo todo = new Todo(todoID, "Submit Vaccination Status", Date.valueOf(LocalDate.now()), userIDList );

        when(todoRepository.findById(todoID)).thenReturn(Optional.of(todo));

        Todo foundTodo = todoService.getTodo(todoID);

        assertNotNull(foundTodo);
        assertEquals(foundTodo.getCreatedForIds(), userIDList);
    }

    @Test
    void addTodo_Success() {
        UUID userID1 = UUID.randomUUID();
        UUID userID2 = UUID.randomUUID();
        UUID ownerID = UUID.randomUUID();

        List<UUID> userIDList = new ArrayList<>();
        userIDList.add(userID1);
        userIDList.add(userID2);

        UUID todoID = UUID.randomUUID();
        Todo todo = new Todo(todoID, "Submit Vaccination Status", Date.valueOf(LocalDate.now()), userIDList );

        when(todoRepository.save(any(Todo.class))).thenReturn(todo);

        Todo foundTodo = todoService.addTodo(ownerID, todo);

        assertNotNull(foundTodo);
        assertEquals(todo.getCreatedForIds(), foundTodo.getCreatedForIds());
    }

    @Test
    void updateTodo_Success() {
        UUID userID1 = UUID.randomUUID();
        UUID userID2 = UUID.randomUUID();

        List<UUID> userIDList = new ArrayList<>();
        userIDList.add(userID1);
        userIDList.add(userID2);

        UUID todoID = UUID.randomUUID();
        Todo oldTodo = new Todo(todoID, "Submit Vaccination Status", Date.valueOf(LocalDate.now()), userIDList );
        Todo newTodo = new Todo(todoID, "Update Work Schedule", Date.valueOf(LocalDate.now().plusDays(2)), userIDList );

        when(todoRepository.findById(any(UUID.class))).thenReturn(Optional.of(oldTodo));
        when(todoRepository.save(oldTodo)).thenReturn(newTodo);

        todoService.updateTodo(todoID, newTodo);

        verify(todoRepository, atLeast(1)).findById(todoID);
        verify(todoRepository).save(oldTodo);
    }

    @Test
    void deleteTodo_Success() {
        UUID userID1 = UUID.randomUUID();
        UUID userID2 = UUID.randomUUID();

        List<UUID> userIDList = new ArrayList<>();
        userIDList.add(userID1);
        userIDList.add(userID2);

        UUID todoID = UUID.randomUUID();
        Todo oldTodo = new Todo(todoID, "Submit Vaccination Status", Date.valueOf(LocalDate.now()), userIDList );

        doNothing().when(todoRepository).deleteById(todoID);

        todoService.deleteTodo(oldTodo.getId());
        verify(todoRepository).deleteById(oldTodo.getId());
    }
}
