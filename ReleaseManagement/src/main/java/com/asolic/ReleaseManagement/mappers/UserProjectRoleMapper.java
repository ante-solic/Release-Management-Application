package com.asolic.ReleaseManagement.mappers;

import com.asolic.ReleaseManagement.dto.UserProjectRoleDto;
import com.asolic.ReleaseManagement.models.UserProjectRole;

public interface UserProjectRoleMapper {
    UserProjectRoleDto toDto(UserProjectRole userProjectRole);
    UserProjectRole toEntity(UserProjectRoleDto userProjectRoleDto);
}
