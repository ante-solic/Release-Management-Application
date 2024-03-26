package com.asolic.ReleaseManagement.controllers;

import com.asolic.ReleaseManagement.exceptions.UserNotFoundException;
import com.asolic.ReleaseManagement.models.User;
import com.asolic.ReleaseManagement.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public String createUser(@RequestBody User user){
        userService.createUser(user);
        return "success";
    }

    @GetMapping("/find")
    public User findUser(@RequestBody User user) throws UserNotFoundException {
        return userService.findUserByUsername(user.getUsername());
    }

    @PostMapping("/update/{id}")
    public User updateUser(@RequestBody User updateUser, @PathVariable UUID id) throws UserNotFoundException{
        return userService.updateUser(updateUser, id);
    }

    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable UUID id) throws UserNotFoundException{
        userService.deleteUser(id);
        return "deleted";
    }
}
