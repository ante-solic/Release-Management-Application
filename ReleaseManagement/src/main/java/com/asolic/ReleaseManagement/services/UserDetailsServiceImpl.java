package com.asolic.ReleaseManagement.services;

import com.asolic.ReleaseManagement.exceptions.UserNotFoundException;
import com.asolic.ReleaseManagement.models.Role;
import com.asolic.ReleaseManagement.models.User;
import com.asolic.ReleaseManagement.repositories.UserRepository;
import com.asolic.ReleaseManagement.security.CustomUserDetails;
import jakarta.persistence.Id;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepository.findByUsername(username).get();

        if (user == null){
            throw new UsernameNotFoundException("User not found!");
        }

        return  new CustomUserDetails(user);
    }
}
