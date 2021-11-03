package com.app.todo;

import com.app.todo.user.*;
import com.app.todo.business.Business;
import com.app.todo.industry.Industry;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    
    @Mock
    private UserRepository users;

    @Mock
    private BCryptPasswordEncoder encoder;

    @InjectMocks
    private UserService userService;

    @Test
    void getAllUsers_ReturnAllUsers() {
        when(users.findAll()).thenReturn(new ArrayList<User>());

        List<User> allUsers = userService.getAllUsers();

        assertNotNull(allUsers);
        verify(users).findAll();
    }

    @Test
    void getUserWithId_ValidUserId_ReturnUser() {
        Industry industry = new Industry("Arts and Culture");
        Business business = new Business("asd789fhgj", "Singapore Museum", industry);
        User user = new User("admin@abc.com", "admin", "goodpassword", "ROLE_ADMIN", business);
        when(users.findById(user.getId())).thenReturn(Optional.of(user));

        User foundUser = userService.getUser(user.getId());

        assertNotNull(foundUser);
        verify(users).findById(user.getId());
    }

    @Test
    void getUserWithId_InvalidUserId_ReturnUserNotFoundException() {
        Industry industry = new Industry("Arts and Culture");
        Business business = new Business("asd789fhgj", "Singapore Museum", industry);
        User user = new User(1L, "admin@abc.com", "admin", encoder.encode("goodpassword"), "ROLE_ADMIN", business);
        userService.addUser(user);
        Long testUserId = 2L;

        when(users.findById(any(Long.class))).thenReturn(Optional.ofNullable(null));

        Throwable exception = null;

        try {
            User foundUser = userService.getUser(testUserId);
        } catch (Throwable ex) {
            exception = ex;
        }

        assertEquals(UserNotFoundException.class, exception.getClass());
        verify(users).findById(testUserId);
    }

    @Test
    void getUserWithEmail_ValidUserEmail_ReturnUser() {
        Industry industry = new Industry("Arts and Culture");
        Business business = new Business("asd789fhgj", "Singapore Museum", industry);
        User user = new User("admin@abc.com", "admin", encoder.encode("goodpassword"), "ROLE_ADMIN", business);
        when(users.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        User foundUser = userService.getUser(user.getEmail());

        assertNotNull(foundUser);
        verify(users).findByEmail(user.getEmail());
    }

    @Test
    void getUserWithEmail_InvalidUserEmail_ReturnUserNotFoundException() {
        Industry industry = new Industry("Arts and Culture");
        Business business = new Business("asd789fhgj", "Singapore Museum", industry);
        User user = new User("admin@abc.com", "admin", encoder.encode("goodpassword"), "ROLE_ADMIN", business);
        userService.addUser(user);
        String testUserEmail = "badmin@abc.com";

        when(users.findByEmail(any(String.class))).thenReturn(Optional.ofNullable(null));

        Throwable exception = null;

        try {
            User foundUser = userService.getUser(testUserEmail);
        } catch (Throwable ex) {
            exception = ex;
        }

        assertEquals(UserNotFoundException.class, exception.getClass());
        verify(users).findByEmail(testUserEmail);
    }

    @Test
    void addUser_NewUser_ReturnSavedUser() {
        Industry industry = new Industry("Arts and Culture");
        Business business = new Business("asd829fhgj", "Singapore Museum", industry);
        User user = new User("admin@abc.com", "admin", encoder.encode("goodpassword"), "ROLE_ADMIN", business);
        when(users.existsByEmail(any(String.class))).thenReturn(false);
        when(users.save(any(User.class))).thenReturn(user);

        User savedUser = userService.addUser(user);

        assertNotNull(savedUser);
        verify(users).existsByEmail(user.getEmail());
        verify(users).save(user);
    }

    @Test
    void addUser_UserWithSameEmail_ReturnUserAlreadyRegisteredException() {
        Industry industry = new Industry("Arts and Culture");
        Business business = new Business("asd789fhgj", "Singapore Museum", industry);
        User user = new User("admin@abc.com", "admin", "goodpassword", "ROLE_ADMIN", business);
        userService.addUser(user);
        User newUser = new User("admin@abc.com", "admin", "goodpassword", "ROLE_ADMIN", business);

        when(users.existsByEmail(any(String.class))).thenReturn(true);

        Throwable exception = null;

        try {
            User savedUser = userService.addUser(newUser);
        } catch (Throwable ex) {
            exception = ex;
        }

        assertEquals(UserAlreadyRegisteredException.class, exception.getClass());
        verify(users, atLeast(2)).existsByEmail(user.getEmail());
    }
}
