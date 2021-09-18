package com.app.todo.todo;

import com.app.todo.todo.ToDo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200/")
public class ToDoController {
    private ToDoServiceImpl toDoService;

    @Autowired
    public ToDoController(ToDoServiceImpl toDoService) {
        this.toDoService = toDoService;
    }

    @GetMapping("/{userId}/todos")
    public List<ToDo> getTodosByUsername(
            @PathVariable (value = "userId") Long userId) {
        return toDoService.findToDoByUserId(userId);
    }

    @GetMapping("/{userId}/todos/{todoId}")
    public ToDo getTodo(
            @PathVariable (value = "userId") Long userId, @PathVariable (value = "todoId") Long todoId) {
        return toDoService.getSpecificToDo(userId, todoId);
    }

    @DeleteMapping("/{userId}/todos/{todoId}")
    public ResponseEntity<Void> deleteTodo(
            @PathVariable (value = "userId") Long userId, @PathVariable (value = "todoId") Long todoId) {
        toDoService.deleteSpecificToDo(userId, todoId);

        // return 204 no content
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{userId}/todos/{todoId}")
    public ResponseEntity<ToDo> updateTodo(
            @PathVariable (value = "userId") Long userId, @PathVariable (value = "todoId") Long todoId, @RequestBody ToDo newToDo) {
        toDoService.updateSpecificToDo(userId, todoId, newToDo);
        return new ResponseEntity<ToDo>(newToDo, HttpStatus.OK);
    }

    // REST standard: Should return URI of new resource
    @PostMapping("/{userId}/todos")
    public ResponseEntity<Void> createTodo(
            @PathVariable (value = "userId") Long userId, @RequestBody ToDo newToDo) {
        ToDo createdNewTodo = toDoService.addToDo(userId, newToDo);

        // append id to new URI
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(createdNewTodo.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }
}
