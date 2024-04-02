package com.asolic.ReleaseManagement.mappers;

import com.asolic.ReleaseManagement.dto.RoleDto;
import com.asolic.ReleaseManagement.models.Role;

public interface RoleMapper {
    RoleDto toDto(Role role);
    Role toEntity(RoleDto roleDto);
}
