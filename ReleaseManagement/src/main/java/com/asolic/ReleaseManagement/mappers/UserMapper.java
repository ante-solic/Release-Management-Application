package com.asolic.ReleaseManagement.mappers;

import com.asolic.ReleaseManagement.dto.UserDto;
import com.asolic.ReleaseManagement.models.User;

public interface UserMapper {
    UserDto toDto(User user);
    User toEntity(UserDto userDto);
}
