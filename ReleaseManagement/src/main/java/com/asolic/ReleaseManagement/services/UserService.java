package com.asolic.ReleaseManagement.services;


import com.asolic.ReleaseManagement.exceptions.UserNotFoundException;
import com.asolic.ReleaseManagement.models.User;

import java.util.UUID;

public interface UserService {
    void createUser(User user);

    User findUserByUsername(String username) throws UserNotFoundException;

    User updateUser(User updatedUser, UUID userId) throws UserNotFoundException;

    void deleteUser(UUID userId) throws UserNotFoundException;
}
