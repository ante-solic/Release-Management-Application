package com.asolic.ReleaseManagement.services;

import com.asolic.ReleaseManagement.dto.ProjectDto;
import com.asolic.ReleaseManagement.exceptions.ProjectNotFoundException;
import com.asolic.ReleaseManagement.models.Project;

import java.util.List;
import java.util.UUID;

public interface ProjectService {

    void createProject(ProjectDto projectDto);

    Project findProjectById(UUID id) throws ProjectNotFoundException;

    Project findProjectByName(String name) throws ProjectNotFoundException;

    List<Project> findAllProjects() throws ProjectNotFoundException;

    Project updateProject(ProjectDto updatedProjectDto, UUID projectId);

    void deleteById(UUID id) throws ProjectNotFoundException;
}
