package com.asolic.ReleaseManagement.mappers;

import com.asolic.ReleaseManagement.dto.RoleDto;
import com.asolic.ReleaseManagement.models.Role;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface RoleMapper {
    RoleDto toDto(Role role);
    Role toEntity(RoleDto roleDto);
}
