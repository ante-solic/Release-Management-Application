package com.asolic.ReleaseManagement.controllers;

import com.asolic.ReleaseManagement.dto.UserDto;
import com.asolic.ReleaseManagement.exceptions.UserNotFoundException;
import com.asolic.ReleaseManagement.models.Role;
import com.asolic.ReleaseManagement.models.User;
import com.asolic.ReleaseManagement.repositories.UserRepository;
import com.asolic.ReleaseManagement.repositories.RoleRepository;
import com.asolic.ReleaseManagement.response.AuthResponse;
import com.asolic.ReleaseManagement.security.JwtProvider;
import com.asolic.ReleaseManagement.services.UserDetailsServiceImpl;
import com.asolic.ReleaseManagement.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/user")
@CrossOrigin
@AllArgsConstructor
public class UserController {
    private UserService userService;
    private PasswordEncoder passwordEncoder;
    private UserDetailsServiceImpl customUserDetails;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

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

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> createUserHandler(@RequestBody UserDto userDto){
        String username = userDto.getUsername();
        String email = userDto.getEmail();
        String password = userDto.getPassword();
        String firstname = userDto.getFirstname();
        String lastname = userDto.getLastname();

        var roleUser = roleRepository.findByName(userDto.getRole());
        Set<Role> roles = new HashSet<>();
        roles.add(roleUser);

        User isUsernameExists = userRepository.findByUsername(username).orElse(null);

        if(isUsernameExists != null){
            throw new NullPointerException("User already exists");
        }

        var createdUser = new User();
        createdUser.setUsername(username);
        createdUser.setEmail(email);
        createdUser.setPassword(passwordEncoder.encode(password));
        createdUser.setFirstname(firstname);
        createdUser.setLastname(lastname);
        createdUser.setRoles(roles);

        User savedUser = userRepository.save(createdUser);
        userRepository.save(savedUser);

        Authentication authentication = new UsernamePasswordAuthenticationToken(email,password);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = JwtProvider.generateToken(authentication);


        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(token);
        authResponse.setMessage("Register Success");
        authResponse.setStatus(true);
        return new ResponseEntity<AuthResponse>(authResponse, HttpStatus.OK);
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> signin(@RequestBody User loginRequest) {
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();

        Authentication authentication = authenticate(username,password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = JwtProvider.generateToken(authentication);
        AuthResponse authResponse = new AuthResponse();

        authResponse.setMessage("Login success");
        authResponse.setJwt(token);
        authResponse.setStatus(true);

        return new ResponseEntity<>(authResponse,HttpStatus.OK);
    }

    private Authentication authenticate(String username, String password){
        UserDetails userDetails = customUserDetails.loadUserByUsername(username);

        if(userDetails == null) {
            System.out.println("Sign in details - null" + userDetails);

            throw new BadCredentialsException("Invalid username and password");
        }
        if(!passwordEncoder.matches(password,userDetails.getPassword())) {
            System.out.println("Sign in userDetails - password mismatch"+userDetails);

            throw new BadCredentialsException("Invalid password");

        }
        return new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
    }
}
