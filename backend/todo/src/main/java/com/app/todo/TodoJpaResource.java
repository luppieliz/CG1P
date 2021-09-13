package com.app.todo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

// HTTP requests to /jpa/{resource} are directed here.
// Communicates with the TodoJpaRepository (that handles many pre-made H2, Spring Data JPA functions)

// Resource class that serves as the REST Controller on the 8080 server for handling requests
// Delegates work onto the service.
@RestController
// allow cross-origin requests (i.e., from port 4200 to 8080) to get past CORS policy
@CrossOrigin(origins = "http://localhost:4200/")
public class TodoJpaResource {

    @Autowired
    private TodoJpaRepository todoJpaRepository;

    // GET ALL
    // REST standard: Should return 200 OK and content of GET response
    @GetMapping("/jpa/users/{username}/todos")
    public List<Todo> getAllTodos(
            @PathVariable String username) {
        return todoJpaRepository.findByUsername(username);
    }

    // GET ID
    // REST standard: Should return 200 OK and content of GET response
    @GetMapping("/jpa/users/{username}/todos/{id}")
    public Todo getTodo(
            @PathVariable String username, @PathVariable long id) {
        return todoJpaRepository.findById(id).get();
    }

    // DELETE
    // REST standard: Should return 204 noContent if success or 404 notFound if fail
    @DeleteMapping("/jpa/users/{username}/todos/{id}")
    public ResponseEntity<Void> deleteTodo(
            @PathVariable String username, @PathVariable long id) {
            todoJpaRepository.deleteById(id);

            // return 204 no content
            return ResponseEntity.noContent().build();

    }

    // PUT (UPDATE)
    // REST standard: Should return 200 OK with content of updated resource
    @PutMapping("/jpa/users/{username}/todos/{id}")
    public ResponseEntity<Todo> updateTodo(
            @PathVariable String username, @PathVariable long id, @RequestBody Todo todo) {
        todo.setUsername(username);
        Todo todoUpdated = todoJpaRepository.save(todo);
        return new ResponseEntity<Todo>(todo, HttpStatus.OK);
    }

    // POST (CREATE)
    // REST standard: Should return URI of new resource
    @PostMapping("/jpa/users/{username}/todos")
    public ResponseEntity<Void> createTodo(
            @PathVariable String username, @RequestBody Todo todo) {
        todo.setUsername(username);
        Todo createdTodo = todoJpaRepository.save(todo);
        // append id to new URI
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(createdTodo.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

}
