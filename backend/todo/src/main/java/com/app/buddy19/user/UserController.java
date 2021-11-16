package com.app.buddy19.user;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successfully retrieved a user/ a list of users"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")})


    @ApiOperation(value = "View a list of all registered users", response = List.class)
    @GetMapping(path = "/user", produces = "application/json")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @ApiOperation(value = "Retrieve a specific user given a user id", response = User.class)
    @GetMapping(path = "/user/{userId}", produces = "application/json")
    public User getUser(@PathVariable UUID userId) {
        return userService.getUser(userId);
    }

    @ApiOperation(value = "View a list of users given a specific business id", response = List.class)
    @GetMapping(path = "/user/business/{businessId}", produces = "application/json")
    public List<User> getUsersByBusiness(@PathVariable UUID businessId) {
        return userService.getUsersByBusiness(businessId);
    }

    @ApiOperation(value = "Retrieve a specific user given a user email", response = User.class)
    @GetMapping(path = "/user/email/{email}", produces = "application/json")
    public User getUser(@PathVariable String email) {
        return userService.getUser(email);
    }

    @ApiOperation(value = "Add a new user", response = User.class)
    @PostMapping(path = "/user", produces = "application/json")
    public User addUser(@Valid @RequestBody User user) {
        return userService.addUser(user);
    }

    @ApiOperation(value = "Update a user", response = User.class)
    @PutMapping(path = "/user", produces = "application/json")
    public User updateUser(@Valid @RequestBody User user) {
        return userService.updateUser(user);
    }
}