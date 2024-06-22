package com.asolic.ReleaseManagement.services;

import com.asolic.ReleaseManagement.dto.ReleaseDto;
import com.asolic.ReleaseManagement.exceptions.ReleaseNotFoundException;
import com.asolic.ReleaseManagement.models.Release;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


public interface ReleaseService {

    void createRelease(ReleaseDto releaseDto);

    Release findReleaseById(UUID id) throws ReleaseNotFoundException;

    Page<Release> findAllReleases(Pageable pageable, String filter);

    Release updateRelease(ReleaseDto updatedReleaseDto, UUID releaseId) throws ReleaseNotFoundException;

    void deleteReleaseById(UUID releaseId) throws ReleaseNotFoundException;
}
