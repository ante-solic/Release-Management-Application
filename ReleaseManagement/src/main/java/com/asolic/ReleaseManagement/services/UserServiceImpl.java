package com.asolic.ReleaseManagement.services;

import com.asolic.ReleaseManagement.exceptions.UserNotFoundException;
import com.asolic.ReleaseManagement.models.User;
import com.asolic.ReleaseManagement.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;

    public void createUser(User user){
        userRepository.save(user);
    }

    public User findUserByUsername(String username) throws UserNotFoundException{
        var user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User Not Found!"));
        return user;
    }

    public User updateUser(User updateUser, UUID userId) throws UserNotFoundException {
        var user = userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("User Not Found!"));

        user.setUsername(updateUser.getUsername());
        user.setEmail(updateUser.getEmail());
        user.setPassword(updateUser.getPassword());
        user.setFirstname(updateUser.getFirstname());
        user.setLastname(updateUser.getLastname());

        return userRepository.save(user);
    }

    public void deleteUser(UUID userId) throws  UserNotFoundException{
        var user = userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("User Not Found!"));
        userRepository.delete(user);
    }
}
