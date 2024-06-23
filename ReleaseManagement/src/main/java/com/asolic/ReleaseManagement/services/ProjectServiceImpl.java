package com.asolic.ReleaseManagement.services;

import com.asolic.ReleaseManagement.dto.ProjectDto;
import com.asolic.ReleaseManagement.exceptions.ProjectNotFoundException;
import com.asolic.ReleaseManagement.exceptions.UserNotFoundException;
import com.asolic.ReleaseManagement.mappers.ProjectMapper;
import com.asolic.ReleaseManagement.models.Project;
import com.asolic.ReleaseManagement.models.Role;
import com.asolic.ReleaseManagement.models.User;
import com.asolic.ReleaseManagement.repositories.ProjectRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ProjectServiceImpl implements ProjectService{
    private final ProjectRepository projectRepository;
    private final UserService userService;
    private final ProjectMapper projectMapper;


    public void createProject(ProjectDto projectDto) {
        try {
            var user = userService.findUserByUsername(projectDto.getUsername());

            var project = projectMapper.toEntity(projectDto);

            user.getProjects().add(project);

            projectRepository.save(project);
        } catch (UserNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public Project findProjectById(UUID id) throws ProjectNotFoundException{
        return projectRepository.findById(id).orElseThrow(() -> new ProjectNotFoundException("Project Not Found"));
    }

    public Project findProjectByName(String name) throws ProjectNotFoundException{
        return projectRepository.findByName(name).orElseThrow(() -> new ProjectNotFoundException("Project Not Found"));
    }

    public Page<Project> findAllProjects(Pageable pageable, String filter){
        if (filter != null && !filter.isEmpty()) {
            return projectRepository.findByNameContaining(filter, pageable);
        }

        return projectRepository.findAll(pageable);
    }

    public Project updateProject(ProjectDto updatedProjectDto, UUID projectId){
        Project project = projectRepository.findById(projectId).get();

        project.setName(updatedProjectDto.getName());

        return projectRepository.save(project);
    }

    public void deleteById(UUID id) throws ProjectNotFoundException{
        var project = projectRepository.findById(id).orElseThrow(() -> new ProjectNotFoundException("Project Not Found"));
        projectRepository.delete(project);
    }
}
