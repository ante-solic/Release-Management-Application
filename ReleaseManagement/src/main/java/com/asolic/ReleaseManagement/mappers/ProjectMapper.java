package com.asolic.ReleaseManagement.mappers;

import com.asolic.ReleaseManagement.dto.ProjectDto;
import com.asolic.ReleaseManagement.models.Project;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface ProjectMapper {
    ProjectDto toDto(Project project);
    Project toEntity(ProjectDto projectDto);
}
