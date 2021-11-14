package com.app.todo;

import com.app.todo.business.BusinessService;
import com.app.todo.user.*;
import com.app.todo.business.Business;
import com.app.todo.industry.Industry;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

    @Mock
    private BusinessService businessService;

    @InjectMocks
    private UserService userService;

    @Test
    void getAllUsers_ReturnAllUsers() {
        Industry industry = new Industry("Arts and Culture");
        Business business = new Business("asd789fhgj", "Singapore Museum", industry);
        UUID userID = UUID.randomUUID();
        User user = new User(userID,"admin@abc.com", "admin", "goodpassword", "ROLE_ADMIN", business);
        List<User> userList = new ArrayList<>();
        userList.add(user);

        when(users.findAll()).thenReturn(userList);

        List<User> allUsers = userService.getAllUsers();

        assertNotNull(allUsers);
        assertEquals(userList.size(), allUsers.size());
        verify(users).findAll();
    }

    @Test
    void getUserWithId_ValidUserId_ReturnUser() {
        Industry industry = new Industry("Arts and Culture");
        Business business = new Business("asd789fhgj", "Singapore Museum", industry);
        UUID userID = UUID.randomUUID();
        User user = new User(userID,"admin@abc.com", "admin", "goodpassword", "ROLE_ADMIN", business);
        when(users.findById(user.getId())).thenReturn(Optional.of(user));

        User foundUser = userService.getUser(user.getId());

        assertNotNull(foundUser);
        verify(users).findById(user.getId());
    }

    @Test
    void getUserWithId_InvalidUserId_ReturnUserNotFoundException() {
        Industry industry = new Industry("Arts and Culture");
        Business business = new Business("asd789fhgj", "Singapore Museum", industry);
        User user = new User(UUID.randomUUID(), "admin@abc.com", "admin", encoder.encode("goodpassword"), "ROLE_ADMIN", business);
        userService.addUser(user);
        UUID testUserId = UUID.randomUUID();

        when(users.findById(any(UUID.class))).thenReturn(Optional.ofNullable(null));

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

        when(users.existsByEmail(any(String.class))).thenReturn(true);

        Throwable exception = null;

        try {
            User savedUser = userService.addUser(user);
        } catch (Throwable ex) {
            exception = ex;
        }

        assertEquals(UserAlreadyRegisteredException.class, exception.getClass());
        verify(users, atLeast(1)).existsByEmail(user.getEmail());
    }

    @Test
    void getUsersByBusiness_ValidBusinessId_ReturnUserList() {
        Industry industry = new Industry("Arts and Culture");
        UUID businessID = UUID.randomUUID();
        Business business = new Business(businessID, "asd789fhgj", "Singapore Museum", industry);
        User user = new User("admin@abc.com", "admin", encoder.encode("goodpassword"), "ROLE_ADMIN", business);
        List<User> userList = new ArrayList<>();
        userList.add(user);

        when(businessService.getBusiness(any(UUID.class))).thenReturn(business);
        when(users.findByBusiness(business)).thenReturn(userList);

        List<User> foundUsers = userService.getUsersByBusiness(business.getId());

        assertNotNull(foundUsers);
        verify(users).findByBusiness(business);
    }
}
