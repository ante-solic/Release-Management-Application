package com.asolic.ReleaseManagement.controllers;

import com.asolic.ReleaseManagement.exceptions.ProjectNotFoundException;
import com.asolic.ReleaseManagement.models.Project;
import com.asolic.ReleaseManagement.services.ProjectService;
import com.asolic.ReleaseManagement.services.ProjectServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/project")
public class ProjectController {
    @Autowired
    private ProjectService projectService;

    @PostMapping("/create")
    public String createProject(@RequestBody Project project){
        projectService.createProject(project.getName());
        return "success";
    }

    @GetMapping("/find")
    public Project getProject(@RequestBody Project project) throws ProjectNotFoundException {
        return projectService.findProjectById(project.getId());
    }

    @PostMapping("/update/{projectId}")
    public Project updateProject(@RequestBody Project updatedProject, @PathVariable UUID projectId){
        return projectService.updateProject(updatedProject, projectId);
    }

    @PostMapping("/delete/{projectId}")
    public String deleteProject(@PathVariable UUID projectId) throws ProjectNotFoundException{
        projectService.deleteById(projectId);
        return "deleted";
    }
}
