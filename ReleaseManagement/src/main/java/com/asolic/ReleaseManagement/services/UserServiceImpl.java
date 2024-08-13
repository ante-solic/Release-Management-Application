package com.asolic.ReleaseManagement.services;

import com.asolic.ReleaseManagement.dto.UserDto;
import com.asolic.ReleaseManagement.exceptions.UserNotFoundException;
import com.asolic.ReleaseManagement.mappers.UserMapper;
import com.asolic.ReleaseManagement.models.Project;
import com.asolic.ReleaseManagement.models.Role;
import com.asolic.ReleaseManagement.models.User;
import com.asolic.ReleaseManagement.repositories.ProjectRepository;
import com.asolic.ReleaseManagement.repositories.RoleRepository;
import com.asolic.ReleaseManagement.repositories.UserRepository;
import jakarta.transaction.Transactional;
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
    private final ProjectRepository projectRepository;
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
        System.out.println("Fetched User ID: " + user.getId());
        return user;
    }

    public Page<User> findAllUsers(Pageable pageable, String filter){
        if (filter != null && !filter.isEmpty()) {
            return userRepository.findByUsernameContaining(filter, pageable);
        }

        return userRepository.findAll(pageable);
    }

    public User updateUser(UserDto updateUserDto, UUID userId) throws UserNotFoundException {
        var currentUser = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if (updateUserDto.getUsername() != null) {
            currentUser.setUsername(updateUserDto.getUsername());
        }
        if (updateUserDto.getEmail() != null) {
            currentUser.setEmail(updateUserDto.getEmail());
        }
        if (updateUserDto.getFirstname() != null) {
            currentUser.setFirstname(updateUserDto.getFirstname());
        }
        if (updateUserDto.getLastname() != null) {
            currentUser.setLastname(updateUserDto.getLastname());
        }
        if (updateUserDto.getPassword() != null && !updateUserDto.getPassword().isEmpty()) {
            currentUser.setPassword(passwordEncoder.encode(updateUserDto.getPassword()));
        }

        return userRepository.save(currentUser);
    }

    public void updateUserRole(UUID userId, UUID roleId) {
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        var role = roleRepository.findById(roleId).get();

        user.getRoles().clear();
        user.getRoles().add(role);
        userRepository.save(user);
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

    public Page<User> findAllAssignedUsers(Pageable pageable, String filter, UUID projectId){
        if (filter != null && !filter.isEmpty()) {
            return userRepository.findByUsernameContaining(filter, pageable);
        }

        return userRepository.findAllByProjectId(projectId, pageable);
    }

    public List<User> findAllUnassignedUsers(UUID projectId){
        return userRepository.findAllNotInProject(projectId);
    }

    public void assignUser(UUID userId, UUID projectId){
        var user = userRepository.findById(userId).get();

        var project = projectRepository.findById(projectId).get();

        Set<Project> projects = user.getProjects();
        projects.add(project);

        user.setProjects(projects);

        userRepository.save(user);
    }

    public void unassignUser(UUID userId, UUID projectId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        System.out.println("Fetched User: " + user);
        System.out.println("Fetched Project: " + project);

        // Log state of entities
        System.out.println("User's Projects Before Removal: " + user.getProjects());

        // Remove Project from User
        boolean removedFromUser = user.getProjects().remove(project);
        if (removedFromUser) {
            userRepository.save(user);
            System.out.println("User's Projects After Removal: " + user.getProjects());
        } else {
            System.out.println("Project was not found in user's projects.");
        }
    }


}
