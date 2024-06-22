package com.asolic.ReleaseManagement.controllers;

import com.asolic.ReleaseManagement.dto.ProjectDto;
import com.asolic.ReleaseManagement.exceptions.ProjectNotFoundException;
import com.asolic.ReleaseManagement.models.Project;
import com.asolic.ReleaseManagement.services.ProjectService;
import com.asolic.ReleaseManagement.services.ProjectServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/project")
public class ProjectController {
    @Autowired
    private ProjectService projectService;

    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('ROLE_PROJECT_MANAGER', 'ROLE_ADMIN')")
    public ResponseEntity<String> createProject(@RequestBody ProjectDto projectDto){
        projectService.createProject(projectDto);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public Project getProject(@PathVariable UUID id) throws ProjectNotFoundException {
        return projectService.findProjectById(id);
    }

    @GetMapping("/find/all")
    public Page<Project> getAllProjects(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir,
            @RequestParam(required = false) String filter) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortDir), sortBy));
        return projectService.findAllProjects(pageable, filter);
    }

    @PutMapping("/update/{projectId}")
    @PreAuthorize("hasAnyRole('ROLE_PROJECT_MANAGER', 'ROLE_ADMIN')")
    public Project updateProject(@RequestBody ProjectDto updatedProjectDto, @PathVariable UUID projectId){
        return projectService.updateProject(updatedProjectDto, projectId);
    }

    @DeleteMapping("/delete/{projectId}")
    @PreAuthorize("hasAnyRole('ROLE_PROJECT_MANAGER', 'ROLE_ADMIN')")
    public ResponseEntity<String> deleteProject(@PathVariable UUID projectId) throws ProjectNotFoundException{
        projectService.deleteById(projectId);
        return new ResponseEntity<>("Deleted", HttpStatus.OK);
    }
}
