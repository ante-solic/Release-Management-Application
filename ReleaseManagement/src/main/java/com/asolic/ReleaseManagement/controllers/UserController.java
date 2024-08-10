package com.asolic.ReleaseManagement.controllers;

import com.asolic.ReleaseManagement.dto.UserDto;
import com.asolic.ReleaseManagement.exceptions.UserNotFoundException;
import com.asolic.ReleaseManagement.models.User;
import com.asolic.ReleaseManagement.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/user")
@CrossOrigin
@AllArgsConstructor
public class UserController {
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<String> createUser(@RequestBody UserDto userDto){
        userService.createUser(userDto);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public User findUser(@PathVariable UUID id) throws UserNotFoundException {
        return userService.findUser(id);
    }

    @GetMapping("/username/{username}")
    public User findUserByUsername(@PathVariable String username) throws UserNotFoundException{
        return userService.findUserByUsername(username);
    }

    @GetMapping("/find/all")
    @PreAuthorize("hasAnyRole('ROLE_PROJECT_MANAGER', 'ROLE_ADMIN')")
    public Page<User> findAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir,
            @RequestParam(required = false) String filter) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortDir), sortBy));
        return userService.findAllUsers(pageable, filter);
    }

    @GetMapping("/find/all/assigned/{projectId}")
    @PreAuthorize("hasAnyRole('ROLE_PROJECT_MANAGER', 'ROLE_ADMIN')")
    public Page<User> findAllAssignedUsers(
            @RequestParam(defaultValue = "0") int page,
           @RequestParam(defaultValue = "10") int size,
           @RequestParam(defaultValue = "id") String sortBy,
           @RequestParam(defaultValue = "asc") String sortDir,
           @RequestParam(required = false) String filter, @PathVariable UUID projectId) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortDir), sortBy));
        return userService.findAllAssignedUsers(pageable, filter, projectId);
    }

    @GetMapping("/find/all/Unassigned/{projectId}")
    @PreAuthorize("hasAnyRole('ROLE_PROJECT_MANAGER', 'ROLE_ADMIN')")
    public List<User> findAllUnassignedUsers( @PathVariable UUID projectId) {
        return userService.findAllUnassignedUsers(projectId);
    }

    @PostMapping("/assign/{userId}/{projectId}")
    @PreAuthorize("hasAnyRole('ROLE_PROJECT_MANAGER', 'ROLE_ADMIN')")
    public ResponseEntity<String> assignUser(@PathVariable UUID userId, @PathVariable UUID projectId){
        userService.assignUser(userId,projectId);
        return new ResponseEntity<>("User assigned", HttpStatus.OK);
    }

    @DeleteMapping("/unassign/{userId}/{projectId}")
    @PreAuthorize("hasAnyRole('ROLE_PROJECT_MANAGER', 'ROLE_ADMIN')")
    public ResponseEntity<String> unassignUser(@PathVariable UUID userId, @PathVariable UUID projectId){
        userService.unassignUser(userId,projectId);
        return new ResponseEntity<>("User unassigned", HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public User updateUser(@RequestBody UserDto updatedUserDto, @PathVariable UUID id) throws UserNotFoundException{
        return userService.updateUser(updatedUserDto, id);
    }

    @PutMapping("/update/role/{userId}")
    public void updateUserRole(@PathVariable UUID userId, @RequestBody UUID roleId) {
        userService.updateUserRole(userId, roleId);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> deleteUser(@PathVariable UUID id) throws UserNotFoundException{
        userService.deleteUser(id);
        return new ResponseEntity<>("Deleted", HttpStatus.OK);
    }
}
