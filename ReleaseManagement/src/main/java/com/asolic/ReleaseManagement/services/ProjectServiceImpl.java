package com.asolic.ReleaseManagement.services;

import com.asolic.ReleaseManagement.exceptions.ProjectNotFoundException;
import com.asolic.ReleaseManagement.models.Project;
import com.asolic.ReleaseManagement.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProjectServiceImpl implements ProjectService{
    @Autowired
    private ProjectRepository projectRepository;

    public void createProject(String name){
        projectRepository.save(new Project(name));
    }

    public Project findProjectById(UUID id) throws ProjectNotFoundException{
        return projectRepository.findById(id).orElseThrow(() -> new ProjectNotFoundException("Project Not Found"));
    }

    public Project findProjectByName(String name) throws ProjectNotFoundException{
        return projectRepository.findByName(name).orElseThrow(() -> new ProjectNotFoundException("Project Not Found"));
    }

    public Project updateProject(Project updatedProject, UUID projectId){
        Project project = projectRepository.findById(projectId).get();

        project.setName(updatedProject.getName());

        return projectRepository.save(project);
    }

    public void deleteById(UUID id) throws ProjectNotFoundException{
        var project = projectRepository.findById(id).orElseThrow(() -> new ProjectNotFoundException("Project Not Found"));
        projectRepository.delete(project);
    }
}
