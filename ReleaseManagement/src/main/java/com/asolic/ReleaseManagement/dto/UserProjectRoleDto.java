package com.asolic.ReleaseManagement.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class UserProjectRoleDto {
    private UUID id;
    private UserDto user;
    private ProjectDto project;
    private RoleDto role;
}
