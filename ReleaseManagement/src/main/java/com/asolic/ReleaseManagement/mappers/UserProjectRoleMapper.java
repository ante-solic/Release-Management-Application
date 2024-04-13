package com.asolic.ReleaseManagement.mappers;

import com.asolic.ReleaseManagement.dto.UserProjectRoleDto;
import com.asolic.ReleaseManagement.models.UserProjectRole;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface UserProjectRoleMapper {
    UserProjectRoleDto toDto(UserProjectRole userProjectRole);
    UserProjectRole toEntity(UserProjectRoleDto userProjectRoleDto);
}
