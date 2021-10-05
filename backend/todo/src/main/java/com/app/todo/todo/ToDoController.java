package com.app.todo.todo;

import com.app.todo.todo.ToDo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@CrossOrigin(origins="http://localhost:4200")
@RestController
public class ToDoController {
    private ToDoServiceImpl toDoService;

    @Autowired
    public ToDoController(ToDoServiceImpl toDoService) {
        this.toDoService = toDoService;
    }

    @GetMapping("/{username}/todos")
    public List<ToDo> getTodosByUsername(
            @PathVariable (value = "username") String username) {
        return toDoService.findToDoByUsername(username);
    }

    @GetMapping("/{username}/todos/{todoId}")
    public ToDo getTodo(
            @PathVariable (value = "username") String username, @PathVariable (value = "todoId") Long todoId) {
        return toDoService.getSpecificToDo(username, todoId);
    }

    @DeleteMapping("/{username}/todos/{todoId}")
    public ResponseEntity<Void> deleteTodo(
            @PathVariable (value = "username") String username, @PathVariable (value = "todoId") Long todoId) {
        toDoService.deleteSpecificToDo(username, todoId);

        // return 204 no content
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{username}/todos/{todoId}")
    public ResponseEntity<ToDo> updateTodo(
            @PathVariable (value = "username") String username, @PathVariable (value = "todoId") Long todoId, @RequestBody ToDo newToDo) {
        toDoService.updateSpecificToDo(username, todoId, newToDo);
        return new ResponseEntity<ToDo>(newToDo, HttpStatus.OK);
    }

    // REST standard: Should return URI of new resource
    @PostMapping("/{username}/todos/-1")
    public void createTodo(
            @PathVariable (value = "username") String username, @RequestBody ToDo newToDo) {
        toDoService.addToDo(username, newToDo);
    }

//    public ResponseEntity<Void> createTodo(
//            @PathVariable (value = "username") String username, @RequestBody ToDo newToDo) {
//        ToDo createdNewTodo = toDoService.addToDo(username, newToDo);
//
//        // append id to new URI
//        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
//                .path("/{id}").buildAndExpand(createdNewTodo.getId()).toUri();
//        return ResponseEntity.created(uri).build();
//    }
}
