package com.asolic.ReleaseManagement.controllers;

import com.asolic.ReleaseManagement.dto.ProjectDto;
import com.asolic.ReleaseManagement.exceptions.ProjectNotFoundException;
import com.asolic.ReleaseManagement.models.Project;
import com.asolic.ReleaseManagement.services.ProjectService;
import com.asolic.ReleaseManagement.services.ProjectServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/project")
public class ProjectController {
    @Autowired
    private ProjectService projectService;

    @PostMapping("/create")
    public String createProject(@RequestBody ProjectDto projectDto){
        projectService.createProject(projectDto.getName());
        return "success";
    }

    @GetMapping("/find")
    public Project getProject(@RequestBody ProjectDto projectDto) throws ProjectNotFoundException {
        return projectService.findProjectById(projectDto.getId());
    }

    @GetMapping("/find/all")
    public List<Project> getAllProjects() throws ProjectNotFoundException{
        return projectService.findAllProjects();
    }

    @PostMapping("/update/{projectId}")
    public Project updateProject(@RequestBody ProjectDto updatedProjectDto, @PathVariable UUID projectId){
        return projectService.updateProject(updatedProjectDto, projectId);
    }

    @PostMapping("/delete/{projectId}")
    public String deleteProject(@PathVariable UUID projectId) throws ProjectNotFoundException{
        projectService.deleteById(projectId);
        return "deleted";
    }
}
