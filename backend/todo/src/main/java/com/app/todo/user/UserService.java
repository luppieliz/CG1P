package com.app.todo.user;

import com.app.todo.business.BusinessNotFoundException;

import java.util.List;
import java.util.UUID;

public interface UserService {
    List<User> getAllUsers();

    List<User> getAllUsersById(List<UUID> userIds);

    User getUser(UUID userId) throws UserNotFoundException;

    User getUser(String email) throws UserNotFoundException;

    List<User> getUsersByBusiness(UUID businessId) throws BusinessNotFoundException;

    User addUser(User user) throws UserAlreadyRegisteredException;

    User updateUser(User updatedUser) throws UserNotFoundException;
}
