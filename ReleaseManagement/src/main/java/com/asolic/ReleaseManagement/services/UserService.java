package com.asolic.ReleaseManagement.services;


import com.asolic.ReleaseManagement.dto.UserDto;
import com.asolic.ReleaseManagement.exceptions.UserNotFoundException;
import com.asolic.ReleaseManagement.models.User;

import java.util.List;
import java.util.UUID;

public interface UserService {
    void createUser(UserDto userDto);

    User findUserByUsername(String username) throws UserNotFoundException;

    List<User> findAllUsers() throws UserNotFoundException;

    User updateUser(UserDto updatedUserDto, UUID userId) throws UserNotFoundException;

    void deleteUser(UUID userId) throws UserNotFoundException;
}
