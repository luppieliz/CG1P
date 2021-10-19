package com.app.todo.user;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins={ "http://localhost:3000", "http://localhost:4200" })
@RestController
@RequestMapping("/user")
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;

    }
    @ApiOperation(value = "Get a list of registered user")
    @GetMapping(produces = "application/json")
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @ApiOperation(value = "Create a new user")
    @PostMapping(produces = "application/json")
    public User addUser(@Valid @RequestBody User newUser){
        return userService.addUser(newUser);
    }

    @ApiOperation(value = "Delete a specific user")
    @DeleteMapping(path = "/{userId}",  produces = "application/json")
    public User deleteUser(@Valid @PathVariable (value = "userId") Long userId) {
        return userService.deleteUser(userId);
    }
}
