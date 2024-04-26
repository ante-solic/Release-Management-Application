package com.asolic.ReleaseManagement.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class UserDto {
    private UUID id;
    private String username;
    private String email;
    private String password;
    private String firstname;
    private String lastname;
    private String role;
}
