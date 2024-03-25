package com.asolic.ReleaseManagement.services;

import com.asolic.ReleaseManagement.exceptions.ProjectNotFoundException;
import com.asolic.ReleaseManagement.models.Project;

import java.util.UUID;

public interface ProjectService {

    void createProject(String name);

    Project findProjectById(UUID id) throws ProjectNotFoundException;

    Project findProjectByName(String name) throws ProjectNotFoundException;

    Project updateProject(Project updatedProject, UUID projectId);

    void deleteById(UUID id) throws ProjectNotFoundException;
}
