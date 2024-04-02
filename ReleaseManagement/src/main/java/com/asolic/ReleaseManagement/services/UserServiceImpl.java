package com.asolic.ReleaseManagement.services;

import com.asolic.ReleaseManagement.dto.UserDto;
import com.asolic.ReleaseManagement.exceptions.UserNotFoundException;
import com.asolic.ReleaseManagement.mappers.UserMapper;
import com.asolic.ReleaseManagement.models.User;
import com.asolic.ReleaseManagement.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;
    private UserMapper userMapper;

    public void createUser(UserDto userDto){
        var user = userMapper.toEntity(userDto);
        userRepository.save(user);
    }

    public User findUserByUsername(String username) throws UserNotFoundException{
        var user = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException("User Not Found!"));
        return user;
    }

    public List<User> findAllUsers() throws UserNotFoundException{
        var users = userRepository.findAll();

        if(users.isEmpty()){
            throw new UserNotFoundException("No users found!");
        }

        return users;
    }

    public User updateUser(UserDto updateUserDto, UUID userId) throws UserNotFoundException {
        userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not Found"));
        updateUserDto.setId(userId);

        var user = userMapper.toEntity(updateUserDto);

        return userRepository.save(user);
    }

    public void deleteUser(UUID userId) throws  UserNotFoundException{
        var user = userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("User Not Found!"));
        userRepository.delete(user);
    }
}
