package com.asolic.ReleaseManagement.services;

import com.asolic.ReleaseManagement.dto.UserDto;
import com.asolic.ReleaseManagement.exceptions.UserNotFoundException;
import com.asolic.ReleaseManagement.mappers.UserMapper;
import com.asolic.ReleaseManagement.models.Role;
import com.asolic.ReleaseManagement.models.User;
import com.asolic.ReleaseManagement.repositories.RoleRepository;
import com.asolic.ReleaseManagement.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public void createUser(UserDto userDto){
        var user = userMapper.toEntity(userDto);
        userRepository.save(user);
    }

    public User findUser(UUID id) throws UserNotFoundException{
        var user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User Not Found!"));
        return user;
    }

    public User findUserByUsername(String username) throws UserNotFoundException{
        var user = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException("User Not Found!"));
        return user;
    }

    public Page<User> findAllUsers(Pageable pageable, String filter){
        if (filter != null && !filter.isEmpty()) {
            return userRepository.findByUsernameContaining(filter, pageable);
        }

        return userRepository.findAll(pageable);
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

    public User registerUser(UserDto userDto){

        var roleUser = roleRepository.findByName(userDto.getRole());
        Set<Role> roles = new HashSet<>();
        roles.add(roleUser);

        var isUsernameExists = userRepository.findByUsername(userDto.getUsername()).orElse(null);

        if(isUsernameExists != null){
            throw new NullPointerException("User already exists");
        }

        var createdUser = new User();
        createdUser.setUsername(userDto.getUsername());
        createdUser.setEmail(userDto.getEmail());
        createdUser.setPassword(passwordEncoder.encode(userDto.getPassword()));
        createdUser.setFirstname(userDto.getFirstname());
        createdUser.setLastname(userDto.getLastname());
        createdUser.setRoles(roles);

        return userRepository.save(createdUser);
    }
}
