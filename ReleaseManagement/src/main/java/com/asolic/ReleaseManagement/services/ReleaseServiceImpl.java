package com.asolic.ReleaseManagement.services;

import com.asolic.ReleaseManagement.dto.ReleaseDto;
import com.asolic.ReleaseManagement.exceptions.ReleaseNotFoundException;
import com.asolic.ReleaseManagement.mappers.FeatureMapper;
import com.asolic.ReleaseManagement.mappers.ReleaseMapper;
import com.asolic.ReleaseManagement.models.Release;
import com.asolic.ReleaseManagement.repositories.ReleaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.module.ResolutionException;
import java.util.List;
import java.util.UUID;

@Service
public class ReleaseServiceImpl implements ReleaseService{
    @Autowired
    private ReleaseRepository releaseRepository;

    private final ReleaseMapper releaseMapper;

    public ReleaseServiceImpl(ReleaseMapper releaseMapper){
        this.releaseMapper = releaseMapper;
    }

    public void createRelease(ReleaseDto releaseDto){
        var release = releaseMapper.toEntity(releaseDto);
        releaseRepository.save(release);
    }

    public Release findReleaseById(UUID id) throws ReleaseNotFoundException {
        return releaseRepository.findById(id).orElseThrow(() -> new ReleaseNotFoundException("Release not found!"));
    }

    public List<Release> findAllReleases() throws ReleaseNotFoundException{
        var releases = releaseRepository.findAll();

        if(releases.isEmpty()){
            throw new ReleaseNotFoundException("No releases found!");
        }

        return releases;
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
