package com.asolic.ReleaseManagement.services;


import com.asolic.ReleaseManagement.dto.UserDto;
import com.asolic.ReleaseManagement.exceptions.UserNotFoundException;
import com.asolic.ReleaseManagement.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface UserService{
    void createUser(UserDto userDto);

    User findUser(UUID id) throws UserNotFoundException;

    User findUserByUsername(String username) throws UserNotFoundException;

    Page<User> findAllUsers(Pageable pageable, String filter);

    Page<User> findAllAssignedUsers(Pageable pageable, String filter, UUID projectId);

    List<User> findAllUnassignedUsers(UUID projectId);

    void assignUser(UUID userId, UUID projectId);

    void unassignUser(UUID userId, UUID projectId);

    User updateUser(UserDto updatedUserDto, UUID userId) throws UserNotFoundException;
    void updateUserRole(UUID userId, UUID roleId);
    void deleteUser(UUID userId) throws UserNotFoundException;
    User registerUser(UserDto userDto);
}
