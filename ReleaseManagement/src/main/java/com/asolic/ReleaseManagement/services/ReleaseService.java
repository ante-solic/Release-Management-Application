package com.asolic.ReleaseManagement.services;

import com.asolic.ReleaseManagement.exceptions.ReleaseNotFoundException;
import com.asolic.ReleaseManagement.models.Release;
import org.springframework.stereotype.Service;

import java.util.UUID;


public interface ReleaseService {

    void createRelease(Release release);

    Release findReleaseById(UUID id) throws ReleaseNotFoundException;

    Release updateRelease(Release updatedRelease, UUID releaseId);

    void deleteReleaseById(UUID releaseId) throws ReleaseNotFoundException;
}
