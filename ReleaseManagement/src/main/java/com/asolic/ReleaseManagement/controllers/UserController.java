package com.asolic.ReleaseManagement.controllers;

import com.asolic.ReleaseManagement.dto.UserDto;
import com.asolic.ReleaseManagement.exceptions.UserNotFoundException;
import com.asolic.ReleaseManagement.models.User;
import com.asolic.ReleaseManagement.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.swing.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public String createUser(@RequestBody UserDto userDto){
        userService.createUser(userDto);
        return "success";
    }

    @GetMapping("/{id}")
    public User findUser(@PathVariable UUID id) throws UserNotFoundException {
        return userService.findUser(id);
    }

    @GetMapping("/find/all")
    public List<User> findAllUsers() throws UserNotFoundException{
        return userService.findAllUsers();
    }

    @PutMapping("/update/{id}")
    public User updateUser(@RequestBody UserDto updatedUserDto, @PathVariable UUID id) throws UserNotFoundException{
        return userService.updateUser(updatedUserDto, id);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteUser(@PathVariable UUID id) throws UserNotFoundException{
        userService.deleteUser(id);
        return "deleted";
    }
}
