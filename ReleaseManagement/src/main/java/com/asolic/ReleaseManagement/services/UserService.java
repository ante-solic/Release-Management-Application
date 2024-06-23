package com.asolic.ReleaseManagement.services;


import com.asolic.ReleaseManagement.dto.UserDto;
import com.asolic.ReleaseManagement.exceptions.UserNotFoundException;
import com.asolic.ReleaseManagement.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface UserService{
    void createUser(UserDto userDto);

    User findUser(UUID id) throws UserNotFoundException;

    User findUserByUsername(String username) throws UserNotFoundException;

    Page<User> findAllUsers(Pageable pageable, String filter);

    User updateUser(UserDto updatedUserDto, UUID userId) throws UserNotFoundException;

    void deleteUser(UUID userId) throws UserNotFoundException;

    User registerUser(UserDto userDto);
}
