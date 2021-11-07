package com.app.todo.todo;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import com.app.todo.user.User;
import com.app.todo.user.UserNotAuthenticatedException;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class ToDoController {
    private ToDoServiceImpl toDoService;

    @Autowired
    public ToDoController(ToDoServiceImpl toDoService) {
        this.toDoService = toDoService;
    }

    @ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved todo list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })

    private void validateUser(Long authenticatedUserId, Long targetUserId) throws UserNotAuthenticatedException {
        if (authenticatedUserId != targetUserId) {
            throw new UserNotAuthenticatedException();
        }
    }

    @ApiOperation(value = "View a list of todos generated by a specific user", response = Iterable.class)
    @GetMapping(path = "/{userId}/todos", produces = "application/json")
    public List<ToDo> getTodosByUserId(@AuthenticationPrincipal User authenticatedUser, @PathVariable Long userId) {
        validateUser(authenticatedUser.getId(), userId);
        return toDoService.getTodosByUserId(userId);
    }

    @ApiOperation(value = "View a specific todo of a specific user based on its id")
    @GetMapping(path = "/{userId}/todos/{todoId}", produces = "application/json")
    public ToDo getTodo(@AuthenticationPrincipal User authenticatedUser, @PathVariable Long userId,
            @PathVariable Long todoId) {
        validateUser(authenticatedUser.getId(), userId);
        return toDoService.getTodo(todoId);
    }

    @ApiOperation(value = "Create a new todo by a specific user")
    @PostMapping(path = "/{userId}/todos", produces = "application/json")
    public ResponseEntity<Void> addTodo(@AuthenticationPrincipal User authenticatedUser, @PathVariable Long userId,
            @Valid @RequestBody ToDo newTodo) {
        validateUser(authenticatedUser.getId(), userId);
        ToDo createdNewTodo = toDoService.addTodo(userId, newTodo);

        // append id to new URI
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(createdNewTodo.getId())
                .toUri();
        return ResponseEntity.created(uri).build();
    }

    @ApiOperation(value = "Update a specific todo of a specific user based on its id")
    @PutMapping(path = "/{userId}/todos/{todoId}", produces = "application/json")
    public ResponseEntity<ToDo> updateTodo(@AuthenticationPrincipal User authenticatedUser, @PathVariable Long userId,
            @PathVariable Long todoId, @Valid @RequestBody ToDo newTodo) {
        validateUser(authenticatedUser.getId(), userId);
        toDoService.updateTodo(todoId, newTodo);

        return new ResponseEntity<ToDo>(newTodo, HttpStatus.OK);
    }

    @ApiOperation(value = "Delete a specific todo of a specific user based on its id")
    @DeleteMapping(path = "/{userId}/todos/{todoId}", produces = "application/json")
    public ResponseEntity<Void> deleteTodo(@AuthenticationPrincipal User authenticatedUser, @PathVariable Long userId,
            @PathVariable Long todoId) {
        validateUser(authenticatedUser.getId(), userId);
        toDoService.deleteTodo(todoId);

        // return 204 no content
        return ResponseEntity.noContent().build();
    }
}