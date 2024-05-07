package com.asolic.ReleaseManagement.controllers;

import com.asolic.ReleaseManagement.dto.ProjectDto;
import com.asolic.ReleaseManagement.exceptions.ProjectNotFoundException;
import com.asolic.ReleaseManagement.models.Project;
import com.asolic.ReleaseManagement.services.ProjectService;
import com.asolic.ReleaseManagement.services.ProjectServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/project")
public class ProjectController {
    @Autowired
    private ProjectService projectService;

    @PostMapping("/create")
    public ResponseEntity<String> createProject(@RequestBody ProjectDto projectDto){
        projectService.createProject(projectDto);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public Project getProject(@PathVariable UUID id) throws ProjectNotFoundException {
        return projectService.findProjectById(id);
    }

    @GetMapping("/find/all")
    public List<Project> getAllProjects() throws ProjectNotFoundException{
        return projectService.findAllProjects();
    }

    @PutMapping("/update/{projectId}")
    public Project updateProject(@RequestBody ProjectDto updatedProjectDto, @PathVariable UUID projectId){
        return projectService.updateProject(updatedProjectDto, projectId);
    }

    @DeleteMapping("/delete/{projectId}")
    public ResponseEntity<String> deleteProject(@PathVariable UUID projectId) throws ProjectNotFoundException{
        projectService.deleteById(projectId);
        return new ResponseEntity<>("Deleted", HttpStatus.OK);
    }
}
