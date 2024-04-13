package com.asolic.ReleaseManagement.mappers;

import com.asolic.ReleaseManagement.dto.UserDto;
import com.asolic.ReleaseManagement.models.User;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface UserMapper {
    UserDto toDto(User user);
    User toEntity(UserDto userDto);
}
