package com.app.todo.user;

import com.app.todo.business.BusinessService;
import com.app.todo.business.BusinessNotFoundException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

import java.util.List;

@CrossOrigin(origins={ "http://localhost:3000", "http://localhost:4200" })
@RestController
public class UserController {
    private UserService userService;
    private BusinessService businessService;

    public UserController(UserService user, BusinessService business) {
        this.userService = user;
        this.businessService = business;
    }

    @GetMapping("/business/{businessId}/users")
    public List<User> getAllUsersByBusinessId(@PathVariable (value = "businessId") Long businessId) {
        if (!businessService.existsById(businessId)) {
            throw new BusinessNotFoundException(businessId);
        }
        return userService.getUsersByBusinessId(businessId);
    }

    // @PostMapping("/user")
    // public User addUser(@Valid @RequestBody User newUser){
    //     return userService.addUser(newUser);
    // }

    @PostMapping("/business/{businessId}/users")
    public User addUser(@PathVariable (value = "businessId") Long businessId, @Valid @RequestBody User user) {
        // using "map" to handle the returned Optional object from "findById(businessId)"
        return (User) businessService.findById(businessId).map(business ->{
            user.setBusiness(business);
            return userService.save(user);
        }).orElseThrow(() -> new BusinessNotFoundException(businessId));
    }

    @PutMapping("/business/{businessId}/users/{userId}")
    public User updateUser(@PathVariable (value = "businessId") Long businessId,
                            @PathVariable (value = "userId") Long userId,
                            @Valid @RequestBody User newUser) {
        if (!businessService.existsById(businessId)) {
            throw new BusinessNotFoundException(businessId);
        }
        return (User) userService.getUsersByUserIdAndBusinessId(userId, businessId).map(user -> {
            // user.setUser(newUser);
            user = newUser;
            return userService.save(user);
        }).orElseThrow(() -> new UserNotFoundException(userId));
    }

    /**
     * Use a ResponseEntity to have more control over the response sent to client
     */
    @DeleteMapping("/business/{businessId}/user/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable (value = "businessId") Long businessId,
                              @PathVariable (value = "Id") Long userId) {
        
        if(!businessService.existsById(businessId)) {
            throw new BusinessNotFoundException(businessId);
        }

        return userService.getUsersByUserIdAndBusinessId(userId, businessId).map(user -> {
            userService.deleteUser(userId);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new UserNotFoundException(userId));
    }
}
