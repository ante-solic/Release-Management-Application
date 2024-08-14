package com.asolic.ReleaseManagement.services;

import com.asolic.ReleaseManagement.dto.ReleaseDto;
import com.asolic.ReleaseManagement.exceptions.ProjectNotFoundException;
import com.asolic.ReleaseManagement.exceptions.ReleaseNotFoundException;
import com.asolic.ReleaseManagement.mappers.FeatureMapper;
import com.asolic.ReleaseManagement.mappers.ReleaseMapper;
import com.asolic.ReleaseManagement.models.Project;
import com.asolic.ReleaseManagement.models.Release;
import com.asolic.ReleaseManagement.repositories.ProjectRepository;
import com.asolic.ReleaseManagement.repositories.ReleaseRepository;
import com.asolic.ReleaseManagement.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.lang.module.ResolutionException;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ReleaseServiceImpl implements ReleaseService{
    private final ReleaseRepository releaseRepository;
    private final UserRepository userRepository;
    private final ReleaseMapper releaseMapper;
    private final ProjectRepository projectRepository;

    public void createRelease(ReleaseDto releaseDto){
        var release = releaseMapper.toEntity(releaseDto);
        releaseRepository.save(release);
    }

    public Release findReleaseById(UUID id) throws ReleaseNotFoundException {
        return releaseRepository.findById(id).orElseThrow(() -> new ReleaseNotFoundException("Release not found!"));
    }

    public Page<Release> findAllReleases(Pageable pageable, String filter){

        if (filter != null && !filter.isEmpty()) {
            return releaseRepository.findByNameContaining(filter, pageable);
        }

        return releaseRepository.findAll(pageable);
    }

    public Page<Release> findAllAssignedReleases(Pageable pageable, String filter, UUID userId){
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Set<UUID> userProjectIds = user.getProjects().stream()
                .map(Project::getId)
                .collect(Collectors.toSet());

        List<Release> filteredReleases;

        if (filter != null && !filter.isEmpty()) {
            filteredReleases = releaseRepository.findByNameContaining(filter).stream()
                    .filter(release -> userProjectIds.contains(release.getProject().getId()))
                    .collect(Collectors.toList());
        } else {
            filteredReleases = releaseRepository.findAll().stream()
                    .filter(release -> userProjectIds.contains(release.getProject().getId()))
                    .collect(Collectors.toList());
        }

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), filteredReleases.size());

        if (start > end) {
            return new PageImpl<>(Collections.emptyList(), pageable, filteredReleases.size());
        }

        List<Release> paginatedReleases = filteredReleases.subList(start, end);

        return new PageImpl<>(paginatedReleases, pageable, filteredReleases.size());
    }

    public Page<Release> findAllAssignedProjectReleases(Pageable pageable, String filter, UUID projectId){
        var project = projectRepository.findById(projectId).get();
        if (filter != null && !filter.isEmpty()) {
            return releaseRepository.findByProjectAndNameContaining(project, filter, pageable);
        }

        return releaseRepository.findByProject(project, pageable);
    }

    public Release updateRelease(ReleaseDto updatedReleaseDto, UUID releaseId) throws ReleaseNotFoundException{
        releaseRepository.findById(releaseId).orElseThrow(() -> new ReleaseNotFoundException("Release Not Found"));
        updatedReleaseDto.setId(releaseId);

        var release = releaseMapper.toEntity(updatedReleaseDto);

        return releaseRepository.save(release);
    }

    public void deleteReleaseById(UUID releaseId) throws ReleaseNotFoundException{
        var release = releaseRepository.findById(releaseId).orElseThrow(() -> new ReleaseNotFoundException("Release not found"));
        releaseRepository.delete(release);
    }
}
