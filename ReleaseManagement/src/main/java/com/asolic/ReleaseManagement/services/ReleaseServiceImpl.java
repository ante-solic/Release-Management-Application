package com.asolic.ReleaseManagement.services;

import com.asolic.ReleaseManagement.exceptions.ReleaseNotFoundException;
import com.asolic.ReleaseManagement.models.Release;
import com.asolic.ReleaseManagement.repositories.ReleaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ReleaseServiceImpl implements ReleaseService{
    @Autowired
    private ReleaseRepository releaseRepository;

    public void createRelease(Release release){
        releaseRepository.save(release);
    }

    public Release findReleaseById(UUID id) throws ReleaseNotFoundException {
        return releaseRepository.findById(id).orElseThrow(() -> new ReleaseNotFoundException("Release not found!"));
    }

    public Release updateRelease(Release updatedRelease, UUID releaseId){
        Release release = releaseRepository.findById(releaseId).get();

        release.setName(updatedRelease.getName());
        release.setDescription(updatedRelease.getDescription());
        release.setReleaseDate(updatedRelease.getReleaseDate());
        release.setCreateDate(updatedRelease.getCreateDate());

        return releaseRepository.save(release);
    }

    public void deleteReleaseById(UUID releaseId) throws ReleaseNotFoundException{
        var release = releaseRepository.findById(releaseId).orElseThrow(() -> new ReleaseNotFoundException("Release not found"));
        releaseRepository.delete(release);
    }
}
