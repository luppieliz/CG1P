package com.app.todo.user;

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

    @GetMapping
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @PostMapping
    public User addUser(@Valid @RequestBody User newUser){
        return userService.addUser(newUser);
    }

    @DeleteMapping("/{userId}")
    public User deleteUser(@Valid @PathVariable (value = "userId") Long userId) {
        return userService.deleteUser(userId);
    }
}
